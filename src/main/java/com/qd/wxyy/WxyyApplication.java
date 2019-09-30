package com.qd.wxyy;

import com.qd.wxyy.web.holiday.HolidayService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.qd.wxyy.web.*.", "com.qd.wxyy.wx.*"})
public class WxyyApplication {

    @Autowired
    private HolidayService holidayService;

    public static void main(String[] args) {
        SpringApplication.run(WxyyApplication.class, args);
    }

    @PostConstruct
    public void init() {

//        for (int i = 0; i < 1; i++) {
//            log.info("zhangxd------------------>{}", CommonUtil.getUUid());
//        }

        // 节假日初始化
        //holidayService.initHoliday();

//        WxApi api = new WxApi();
//        String accessToken = api.getAccessToken();
//        api.sendTextMsg(accessToken, "ofWTjvnIlVdgvXgNjOJdvRZPD5Kc", "XXXXXXXXXX");
    }

}
