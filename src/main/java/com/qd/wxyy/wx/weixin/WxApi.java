package com.qd.wxyy.wx.weixin;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号相关方法..
 *
 * @author 张孝党 2019/07/27.
 * @version V1.00.
 * <p>
 * 更新履历： V1.00 2019/07/27 张孝党 创建.
 */
@Slf4j
public class WxApi {

    /**
     * 获取accesstoken.
     */
    public String getAccessToken() {

        String accessToken = "";

        try {
            // 拼接URL
            String url = MessageFormat.format(WeixinConstant.GET_TOKEN_URL, WeixinConstant.APPID, WeixinConstant.APPSECRET);
            log.info("获取accessToken的URL为：{}", url);

            // 发送GET请求
            JSONObject result = JSONObject.parseObject(HttpUtil.getResponseWithGET(url));
            log.info("获取accesstoken的结果为：{}", result);

            if (!result.containsKey("errcode")) {
                accessToken = result.getString("access_token");
            } else {
                log.info("返回异常，信息为：{}", result.getString("errmsg"));
            }
        } catch (Exception ex) {
            log.info("获取accesstoken时异常：{}", ex.getMessage());
        }

        // 返回
        return accessToken;
    }

    /**
     * 发图文字消息.
     */
    public boolean sendTextMsg(String accessToken, String userOpenid, String content) {

        boolean isOK = true;

        try {
            // 组织消息
            JSONObject msg = new JSONObject();
            msg.put("touser", userOpenid);
            msg.put("msgtype", "text");
            Map<String, String> text = new HashMap<>();
            text.put("content", content);
            msg.put("text", text);
            log.info("要发送的文本消息为：{}", msg.toJSONString());

            // 拼接URL
            String url = MessageFormat.format(WeixinConstant.SEND_MESSAGE_URL, accessToken);
            log.info("发送的文本消息为：{}", url);

            // 发送消息
            HttpUtil.getResponseWithPOST(url, msg.toJSONString());
        } catch (Exception ex) {
            log.info("发送消息时异常,异常信息为：{}", ex.getMessage());
            isOK = false;
        }
        return isOK;
    }
}
