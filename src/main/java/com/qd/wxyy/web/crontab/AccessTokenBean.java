package com.qd.wxyy.web.crontab;

import com.qd.wxyy.wx.weixin.WxApi;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
public class AccessTokenBean {

    // token
    private String accessToken = "";

    @Bean
    public void initAccessTokenBean() {
        // 发送信息
        WxApi api = new WxApi();
        // 获取微信订阅号accessToken
        String accessToken = api.getAccessToken();
        log.info("初始化获取的access token为:{}", accessToken);
        this.accessToken = accessToken;
    }
}
