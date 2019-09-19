package com.qd.wxyy.wx.device;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import com.qd.wxyy.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 排队取号.
     */
    public String getOrder(JSONObject requestData) throws Exception {

        // 上传的业务类型
        String reqYwlx = requestData.getString("ywlx");
        // 上传的身份证号
        String reqIdnum = requestData.getString("idnum");
        // 上传的姓名
        String reqName = requestData.getString("name");

        if (reqYwlx.equals("0")) {
            reqYwlx = "01";
        } else {
            reqYwlx = "02";
        }

        // 获取排队信息
        Map<String, String> param1 = new HashMap<>();
        // 当前日期
        param1.put("yyrq", DateTimeUtil.getCurrentDate());
        // 身份证号
        param1.put("yyid", reqIdnum);
        // 业务类型
        param1.put("yylx", reqYwlx);
        Map<String, String> orderInfo = this.deviceRepository.getOrderInfo(param1);

        // 无预约
        if (orderInfo == null) {
            return new SysErrorRsp("0004", "该身份证号今天没有微信预约!").toJsonString();
        }

        // 姓名不匹配
        String getName = orderInfo.get("yyxm");
        if (!getName.equalsIgnoreCase(reqName)) {
            return new SysErrorRsp("0004", "微信预约姓名与该身份证姓名不匹配!").toJsonString();
        }

        // 现在时间
        String currentTime = DateTimeUtil.getCurrentTime().substring(0, 4);
        // 预约开始时间
        String yyStartTime = orderInfo.get("yykssj");
        // 预约结束时间
        String yyEndTime = orderInfo.get("yyjssj");

        // 未到取号时间
        if ((Integer.valueOf(yyStartTime) - Integer.valueOf(currentTime)) > 0) {
            return new SysErrorRsp("0004", "未到预约时间，不能提前取号!").toJsonString();
        }

        // 已超时
        if ((Integer.valueOf(yyEndTime) - Integer.valueOf(currentTime)) < 0) {
            // 更新超时
            this.deviceRepository.updTimeOut(orderInfo.get("id"));
            return new SysErrorRsp("0004", "微信预约已超时，需要提前10分钟取号!").toJsonString();
        }

        // 正常取号时
        // 更新取号流水数据
        Map<String, String> param2 = new HashMap<>();
        param2.put("id", orderInfo.get("id"));
        param2.put("qhsj", DateTimeUtil.getTimeformat());
        param2.put("sfqh", "1");
        this.deviceRepository.updOrderInfo(param2);

        // 返回设备
        JSONObject response = new JSONObject();
        response.put("retcode", "0000");
        response.put("retmsg", "正常取号");
        response.put("yy_tel", orderInfo.get("yysjh"));

//        if(reqYwlx.equals("01")) {
//            response.put("yy_ywlx", "车驾管业务");
//        } else {
//            response.put("yy_ywlx", "违法业务");
//        }
//        response.put("yy_ywlx2", orderInfo.get("ywfl"));
        response.put("yy_ywlx", orderInfo.get("ywfl"));
        response.put("yy_ywlx2", "");

        String yySj = yyStartTime.substring(0, 2) + ":" + yyStartTime.substring(2) + " ~ " + yyEndTime.substring(0, 2) + ":" + yyEndTime.substring(2);
        response.put("yy_sj", yySj);

        // 返回
        return response.toJSONString();
    }

}
