package com.qd.wxyy.web.notice;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.util.DateTimeUtil;
import com.qd.wxyy.wx.weixin.WxApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    /**
     * 获取提示信息.
     */
    public String getMsg() {

        JSONObject response = new JSONObject();

        String msg = this.noticeRepository.getMsg();
        response.put("retcode", "0000");
        response.put("retmsg", "成功");
        response.put("notice", msg);

        // 返回
        return response.toJSONString();
    }

    /**
     * 保存并发送信息.
     */
    @Transactional(rollbackFor = Exception.class)
    public String getEdit(JSONObject requestData) throws Exception {

        // 日期
        String rq = requestData.getString("rq").replace("-", "");
        // 信息
        String notice = requestData.getString("notice");

        // 返回数据
        JSONObject response = new JSONObject();

        // 选择日期要大于当天
        String currentDate = DateTimeUtil.getCurrentDate();
        if (Integer.valueOf(currentDate) >= Integer.valueOf(rq)) {
            response.put("retcode", "0004");
            response.put("retmsg", "取消预约日期必须大于当天!");
            return response.toJSONString();
        }

        // 判断选中的日期是否为节假日
        int isHoliday = this.noticeRepository.isHoliday(rq);
        log.info("指定的日期{}是否在节假日中：{}", rq, isHoliday);
        // 不是节假日
        if (isHoliday == 0) {
            response.put("retcode", "0004");
            response.put("retmsg", "请先在节假日管理中增加指定的日期!");
            return response.toJSONString();
        }

        // 判断选中的日期是否有预约并且未取消的数据
        List<Map<String, String>> yyls = this.noticeRepository.getYyls(rq);
        String rq_tmp = rq.substring(0, 4) + "-" + rq.substring(4, 6) + "-" + rq.substring(6);
        log.info("指定日期{}中预约未取消的预约为：{}。", rq_tmp, yyls);
        // 没有流水
        if (yyls.size() == 0) {
            response.put("retcode", "0004");
            response.put("retmsg", rq_tmp + "没有预约纪录.");
            return response.toJSONString();
        }

        // 根据openid一览发送信息
        sentCancelMsg(requestData.getString("notice"), yyls);

        // 发送完成后删除预约记录
        this.noticeRepository.delYyls(rq);

        // 保存信息
        this.noticeRepository.updMsg(requestData.getString("notice"));
        log.info("保存信息完成");

        response.put("retcode", "0000");
        response.put("retmsg", "发送信息成功!");

        // 返回
        return response.toJSONString();
    }

    /**
     * 根据预约记录发送信息.
     */
    private void sentCancelMsg(String notice, List<Map<String, String>> yyls) throws Exception {

        WxApi api = new WxApi();

        // 获取微信订阅号accessToken
        String accessToken = api.getAccessToken();

        // 循环发送文字消息
        for (Map<String, String> data : yyls) {
            String openId = data.get("openid");
            // 发送消息
            api.sendTextMsg(accessToken, openId, notice);
        }
    }
}
