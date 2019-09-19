package com.qd.wxyy.wx.order;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import com.qd.wxyy.util.CommonUtil;
import com.qd.wxyy.util.DateTimeUtil;
import com.qd.wxyy.util.HttpUtil;
import com.qd.wxyy.util.StringFormat;
import com.qd.wxyy.wx.weixin.WeixinConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 获取用户openid.
     */
    public String getOpenId(String code) throws Exception {

        String openid = "";

        String oaTokenUrl = StringFormat.format(WeixinConstant.OA_TOKEN_URL, WeixinConstant.APPID, WeixinConstant.APPSECRET, code);
        log.info("通过code换取网页授权access_token的url为：" + oaTokenUrl);

        // 发送请求
        JSONObject data = JSONObject.parseObject(HttpUtil.getResponseWithGET(oaTokenUrl));
        log.info("通过code换取网页授权access_token的返回值为：" + data.toJSONString());

        if (data.getString("openid").equals("")) {
            log.error("获取用户openid时出错");
        } else {
            openid = data.getString("openid");
            log.info("获取的openid为{}", openid);
        }

        // 返回
        return openid;
    }

    /**
     * 发送短信验证码.
     */
    public String sendSms(JSONObject requestData) throws Exception {

        // 校验预约次数:三个月内不得超过2次爽约
        JSONObject checkResult = this.checkYycs(requestData.getString("openid"));
        if (!checkResult.getString("retcode").equals("0000")) {
            return checkResult.toJSONString();
        }

        // 判断是否有短信验证，超时时间为10分钟
        if (!checkSms(requestData.getString("openid"), requestData.getString("phone"))) {
            return new SysErrorRsp("0004", "距上条短信还未超过10分钟，请用上次验证码或10分钟后再重试!").toJsonString();
        }

        // 参数
        Map<String, String> paramMap = new HashMap<>();
        // TODO
        // 生成随机验证码
        //String vertifyCode = CommonUtil.getSmsCode(4);
        String vertifyCode = "1111";
        log.info("生成的验证码为：{}", vertifyCode);

//        String host = "http://dingxin.market.alicloudapi.com";
//        String path = "/dx/sendSms";
//        String method = "POST";
//        String appcode = "83a5256f4d45457183c877c3149dd655";
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<>();
//        querys.put("mobile", requestData.getString("phone"));
//        querys.put("param", "code:" + vertifyCode);
//        querys.put("tpl_id", "TP19071714");
//        Map<String, String> bodys = new HashMap<>();
//        HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
//        log.info("发送短信验证码返回的结果为：{}", response.getEntity().getContent());

        paramMap.put("id", CommonUtil.getUUid());
        paramMap.put("openid", requestData.getString("openid"));
        paramMap.put("sjh", requestData.getString("phone"));
        paramMap.put("yzm", vertifyCode);
        paramMap.put("cjsj", DateTimeUtil.getTimeformat());

        // 查询是否存在该用户的验证码信息，有就覆盖没有就新增
        int open = orderRepository.openidQuery(requestData.getString("openid"));
        if (open == 0) {
            orderRepository.addCode(paramMap);
        } else {
            orderRepository.updateCode(paramMap);
        }

        // 返回
        return new SysErrorRsp("0000", "发送成功").toJsonString();
    }

    /**
     * 查询业务一览.
     *
     * @return
     */
    public Object getBusiList() {
        //查询所有的机构信息（包含父业务和子业务）
        List<Map<String, Object>> lstData = orderRepository.getBusiList();
        // 构建业务树
        HashMap<String, Object> busilist = new HashMap<String, Object>();
        this.getJgTree(lstData, "0", busilist);
        log.info("取得的业务信息为:{}", busilist.get("busilist"));
        return busilist.get("busilist");
    }

    /**
     * 获取指定业务的业务数据（包含业务id，业务名称，业务提示信息）.
     *
     * @return
     */
    public Object busiSelectEnd(String busiid) {
        //查询所有的机构信息（包含父业务和子业务）
        Map<String, Object> lstData = orderRepository.busiSelectEnd(busiid);
        log.info("取得的业务信息为:{}", lstData);
        return lstData;
    }

    /**
     * 获取指定业务的业务数据（包含业务id，业务名称，业务提示信息）.
     *
     * @return
     */
    public Object planQuery(String timeid, String date) {
        //查询所有的机构信息（包含父业务和子业务）
        Map<String, Object> lstData = orderRepository.planQuery(timeid);
        lstData.put("date", date);
        log.info("取得的业务信息为:{}", lstData);
        return lstData;
    }

    /**
     * 构建业务树.
     */
    private void getJgTree(List<Map<String, Object>> lstData, String pid, Map<String, Object> treeData) {

        List<Map<String, Object>> childrenData = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : lstData) {
            if (map.get("fjid").toString().equals(pid)) {
                childrenData.add(map);
            }
        }

        if (childrenData.size() != 0) {
            treeData.put("busilist", childrenData);
            for (Map<String, Object> m : childrenData) {
                getJgTree(lstData, m.get("busiid").toString(), m);
            }
        }
    }

    /**
     * 检查上次发送的短信验证码是否超时.
     */
    private boolean checkSms(String openid, String phoneNumber) {

        try {
            Map<String, String> param = new HashMap<>();
            param.put("openid", openid);
            param.put("sjh", phoneNumber);
            Map<String, String> smsInfo = this.orderRepository.getSmsInfo(param);

            // 没有信息时
            if (smsInfo == null) {
                return true;
            }

            // 当前时间
            String currentTime = DateTimeUtil.getTimeformat().substring(0, 12);
            String lashSendTime = smsInfo.get("cjsj").substring(0, 12);
            long diff = Long.valueOf(currentTime) - Long.valueOf(lashSendTime);
            log.info("距上次发短信的时间差{}秒", diff);
            if (diff <= 10) {
                log.info("上次短信未超时");
                return false;
            }

            return true;
        } catch (Exception ex) {
            log.info("检查短信验证码是否超时时出错!");
            return false;
        }
    }

    /**
     * 获取当日预约数量.
     *
     * @return
     */
    public Object dailyQuantity() {
        Map<String, String> lstData = orderRepository.dailyQuantity();
        log.info("取得的业务信息为:{}", lstData);
        return lstData;
    }

    /**
     * 根据openid校验该微信号3个月内是否爽约超过2次.
     */
    public JSONObject checkYycs(String openid) throws Exception {

        JSONObject result = new JSONObject();

        String currentDate = DateTimeUtil.getCurrentDate();

        Map<String, String> param = new HashMap<>();
        param.put("startdate", DateTimeUtil.stepMonth(currentDate, -3));
        param.put("enddate", currentDate);
        param.put("openid", openid);

        int times = this.orderRepository.getYycs(param);
        if (times >= 2) {
            result.put("retcode", "0004");
            result.put("retmsg", "您三个月内已爽约2次，请三个月后再预约!");
        } else {
            result.put("retcode", "0000");
            result.put("retmsg", "正常");
        }

        // 返回
        return result;
    }
}
