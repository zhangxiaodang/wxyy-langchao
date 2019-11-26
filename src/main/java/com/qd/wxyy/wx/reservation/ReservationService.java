package com.qd.wxyy.wx.reservation;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import com.qd.wxyy.util.CommonUtil;
import com.qd.wxyy.util.DateTimeUtil;
import com.qd.wxyy.util.StringFormat;
import com.qd.wxyy.web.ordertime.TimeRepository;
import com.qd.wxyy.wx.order.OrderService;
import com.qd.wxyy.wx.weixin.WxApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ReservationService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * 业务新增.
     */
    @Transactional(rollbackFor = Exception.class)
    public String reservationAdd(JSONObject requestData) throws Exception {

        // 爽约校验
        JSONObject result = this.orderService.checkYycs(requestData.getString("openid"));
        log.info("爽约校验结果为：{}", result.toJSONString());
        if (!result.getString("retcode").equals("0000")) {
            return result.toJSONString();
        }

        // 校验
        JSONObject checkResult = this.checkOrder(requestData);
        log.info("预约前的校验结果为：{}", checkResult.toJSONString());
        if (checkResult.getString("retcode").equals("0004")) {
            return checkResult.toJSONString();
        }

        // 参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("id", CommonUtil.getUUid());
        paramMap.put("openid", requestData.getString("openid"));
        paramMap.put("yyrq", requestData.getString("date"));
        paramMap.put("yykssj", requestData.getString("starttime"));
        paramMap.put("yylx", requestData.getString("busitype"));
        paramMap.put("ywfl", requestData.getString("busiid"));
        paramMap.put("yysjh", requestData.getString("phone"));
        paramMap.put("yyxm", requestData.getString("username"));
        paramMap.put("yyid", requestData.getString("userid"));
        paramMap.put("yycp", requestData.getString("carnumber"));
        paramMap.put("sfqh", "0");
        paramMap.put("bz", "");
        //paramMap.put("qhsj",requestData.getString("qhsj"));
        paramMap.put("sfgq", "0");
        paramMap.put("yyjssj", requestData.getString("endtime"));
        paramMap.put("ywfl2", requestData.getString("businame"));
        paramMap.put("sfqx", "0");

        // 手机验证码校验
//        Map<String, String> code = reservationRepository.getCode(requestData.getString("openid"));
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        int compareTo = 0;
//        try {
//            Date date0 = format.parse(code.get("cjsj"));
//            Date date1 = new Date(date0.getTime() + 300000);
//            Date date2 = format.parse(DateTimeUtil.getTimeformat());
//            compareTo = date1.compareTo(date2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (compareTo == 1) {
//            this.reservationRepository.addReservation(paramMap);
//            // 返回
//            reservationRepository.delCode(code.get("id"));
//            return new SysErrorRsp("0000", "预约成功").toJsonString();
//        } else {
//            return new SysErrorRsp("0004", "验证码过期或输入错误").toJsonString();
//        }

        // 增加一条预约记录
        this.reservationRepository.addReservation(paramMap);

        // 判断该微信用户是否预约过,如果没有则保存用户信息(手机号、姓名、身份证)
        int cnt = this.reservationRepository.getWxUserCnt(requestData.getString("openid"));
        log.info("保存微信用户{}的查询结果为{}", requestData.getString("openid"), cnt);
        // 没有时保存用户信息
        if (cnt == 0) {
            Map<String, String> paramMap1 = new HashMap<>();
            paramMap1.put("id", CommonUtil.getUUid());
            paramMap1.put("openid", requestData.getString("openid"));
            paramMap1.put("yysjh", requestData.getString("phone"));
            paramMap1.put("yyxm", requestData.getString("username"));
            paramMap1.put("yyid", requestData.getString("userid"));
            this.reservationRepository.insertWxUserInfo(paramMap1);
        }

        // 发送信息
        WxApi api = new WxApi();
        // 获取微信订阅号accessToken
        String accessToken = api.getAccessToken();
        // 消息
        String notice = "您好，您已经预约成功。请于{0}{1}-{2}到浪潮集团HR共享服务中心(s05楼南一层)取号办理{3}业务。一人一票，过号无效；请留意窗口叫号";
        notice = StringFormat.format(notice, DateTimeUtil.getCurrentDate2(), "1", "2", requestData.getString("busitype"));
        // 发送消息
        api.sendTextMsg(accessToken, requestData.getString("openid"), notice);

        // 返回
        return new SysErrorRsp("0000", "预约成功").toJsonString();
    }

    /**
     * 业务预约时间查询.
     *
     * @return
     */
    public List<Map<String, Object>> timeSelect() {

        // 定义日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sd = new SimpleDateFormat("EEEE");
        List<Map<String, Object>> treeDatas = new ArrayList<Map<String, Object>>();
        Map<String, Object> treeDatasx = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            Map<String, Object> treeData = new HashMap<>();
            int holiday = reservationRepository.holidayQuery(df.format(getDateAfter(new Date(), i)));
            String weekday = sd.format(getDateAfter(new Date(), i));
            String weekdays = weekday.substring(weekday.length() - 1);

            // 日期
            treeData.put("date", df.format(getDateAfter(new Date(), i)));
            // 星期几
            //treeData.put("weekday", weekdays);
            treeData.put("weekday", DateTimeUtil.dateToWeek(df.format(getDateAfter(new Date(), i))));

            if (holiday == 0) {
                treeData.put("status", "1");
            } else {
                treeData.put("status", "0");
            }
            treeDatas.add(treeData);
        }
        return treeDatas;
    }

    /**
     * 业务预约时间查询.
     */
    public String getordertime(JSONObject requestData) {

        // 当前日期
        String currentDate = DateTimeUtil.getCurrentDate();
        // 当前时间
        String currentTime = DateTimeUtil.getCurrentTime().substring(0, 4);

        HashMap map = new HashMap();
        map.put("date", requestData.getString("date"));
        map.put("busitype", requestData.getString("busitype"));

        List<Map<String, String>> list = reservationRepository.subscribe(map);

        // 最大可预约数量
        int maxRs = Integer.valueOf(this.timeRepository.getGzsj().get("kyyrs"));

        if (currentDate.equals(requestData.getString("date"))) {
            // 是否已过期
            for (Map<String, String> timeMap : list) {
                if (Integer.valueOf(timeMap.get("starttime")) <= Integer.valueOf(currentTime)) {
                    // 已过期
                    timeMap.put("status", "2");
                } else {
                    if (Integer.valueOf(timeMap.get("cnt")) >= maxRs) {
                        // 已满
                        timeMap.put("status", "1");
                    } else {
                        // 空闲
                        timeMap.put("status", "0");
                    }
                }

                timeMap.put("kyyrs", timeMap.get("cnt") + "/" + String.valueOf(maxRs));
            }
        } else {
            for (Map<String, String> data : list) {
                if (Integer.valueOf(data.get("cnt")) >= maxRs) {
                    // 已满
                    data.put("status", "1");
                } else {
                    // 空闲
                    data.put("status", "0");
                }
                data.put("kyyrs", data.get("cnt") + "/" + String.valueOf(maxRs));
            }
        }

        // 判断每个的状态
//        for (Map<String, String> data : list) {
//            if (Integer.valueOf(data.get("cnt")) >= maxRs) {
//                data.put("status", "1");
//            } else {
//                data.put("status", "0");
//            }
//
//            data.put("kyyrs", data.get("cnt") + "/" + String.valueOf(maxRs));
//        }

        JSONObject response = new JSONObject();
        response.put("retcode", "0000");
        response.put("regmsg", "查询成功");
        response.put("timelist", list);

        // 返回
        return response.toJSONString();
    }

    /**
     * 得到几天后的时间
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 预约提交校验.
     */
    public JSONObject checkOrder(JSONObject requestData) {

        JSONObject result = new JSONObject();

        // 校验该身份证号当前时间段是否有预约
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("yyrq", requestData.getString("date"));
        paramMap.put("yykssj", requestData.getString("starttime"));
        paramMap.put("yylx", requestData.getString("busitype"));
        paramMap.put("yyid", requestData.getString("userid"));
        int orderTimes = this.reservationRepository.checkSfyy(paramMap);
        if (orderTimes > 0) {
            result.put("retcode", "0004");
            result.put("retmsg", "该身份证号在该时间段内已预约过，不可重复预约!");
            return result;
        } else {
            result.put("retcode", "0000");
            result.put("retmsg", "校验通过");
        }

        // 返回
        return result;
    }

}
