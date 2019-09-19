package com.qd.wxyy.web.crontab;

import com.qd.wxyy.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务Service.
 *
 * @author 张孝党 2019/07/21.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/21 张孝党 创建.
 */
@Slf4j
@Component
public class CrontabService {

    @Autowired
    private CrontabRepository crontabRepository;

    /**
     * 每5分钟执行一次，将当天预约未取消并超时的纪录修改为已过期.
     */
    @Scheduled(cron = "0 0/10 8-18 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updYyzt() {
        log.info("更新预约状态定时任务开始执行...........");

        try {
            Map<String, String> param = new HashMap<>();
            param.put("yyrq", DateTimeUtil.getCurrentDate());
            param.put("currentTime", DateTimeUtil.getCurrentTime());
            this.crontabRepository.updYyzt(param);

            log.info("更新预约状态定时任务正常结束...........");
        } catch (Exception ex) {
            log.info("更新预约状态定时任务异常结束，异常信息：{}", ex.getMessage());
        }
    }
}
