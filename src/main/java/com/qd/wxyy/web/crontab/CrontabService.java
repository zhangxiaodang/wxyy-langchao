package com.qd.wxyy.web.crontab;

import com.qd.wxyy.util.DateTimeUtil;
import com.qd.wxyy.wx.weixin.WxApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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
     * 每10分钟执行一次，将当天预约未取消并超时的纪录修改为已过期.
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

    /**
     * 每5分钟执行一次，预约时间前30分钟提醒.
     */
    @Scheduled(cron = "0 0/5 8-18 * * ?")
    public void notice() throws Exception {
        log.info("30分钟提前提醒定时任务开始执行...........");

        try {
            Map<String, String> param = new HashMap<>();
            param.put("yyrq", DateTimeUtil.getCurrentDate());
            String currentTime = DateTimeUtil.getCurrentTime2();
            param.put("currenttime", currentTime);
            List<Map<String, String>> orderList = this.crontabRepository.getOrderList(param);
            log.info("共有{}人需要通知", orderList.size());
            // 发送信息
            WxApi api = new WxApi();
            // 获取微信订阅号accessToken
            String accessToken = api.getAccessToken();
            log.info("获取的accessToken为:{}", accessToken);

            orderList.forEach(
                    orderInof -> {
                        try {
                            if (DateTimeUtil.stepMinutes(currentTime, 30).equals(orderInof.get("yykssj"))) {
                                StringBuilder msg = new StringBuilder();
                                msg.append("浪潮集团HR共享服务中心\n\n");
                                msg.append("办理业务名称：" + orderInof.get("ywfl2"));
                                msg.append("\n");
                                msg.append("取票类型：微信预约\n");
                                msg.append("预约时间：");
                                msg.append(orderInof.get("yykssj").substring(0, 2) + ":" + orderInof.get("yykssj").substring(2));
                                msg.append(" - ");
                                msg.append(orderInof.get("yyjssj").substring(0, 2) + ":" + orderInof.get("yyjssj").substring(2));
                                msg.append("\n\n");
                                msg.append("温馨提示：\n");
                                msg.append("一人一票，过号无效；请留意窗口叫号\n");

                                api.sendTextMsg(accessToken, orderInof.get("openid"), msg.toString());
                                log.info("姓名[{}],openid[{}]已通知，信息为{}", orderInof.get("yyxm"), orderInof.get("openid"), msg.toString());
                            }
                        } catch (Exception ex) {
                            log.error("发送消息时异常:{}", ex.getMessage());
                        }
                    }
            );


            log.info("30分钟提前提醒定时任务正常结束...........");
        } catch (Exception ex) {
            log.info("30分钟提前提醒定时任务异常结束，异常信息：{}", ex.getMessage());
        }
    }
}
