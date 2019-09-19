package com.qd.wxyy.web.portal;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import com.qd.wxyy.util.DateTimeUtil;
import com.qd.wxyy.web.holiday.HolidayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录及修改密码Service.
 *
 * @author 张孝党 2019/07/14.
 * @version V0.0.2.
 * <p>
 * 更新履历： V0.0.1 2019/07/14 张孝党 创建.
 */

@Slf4j
@Service
public class PortalService {

    @Autowired
    private PortalRepository portalRepository;

    @Autowired
    private HolidayService holidayService;

    /**
     * 登录验证.
     */
    public String loginVertify(JSONObject requestData) throws Exception {

        // 根据用户id取得用户信息
        String uid = requestData.getString("uid");
        String passwd = requestData.getString("passwd");

        Map<String, String> userInfo = this.portalRepository.getUserInfo(uid);

        // 用户名不存在
        if (userInfo == null) {
            return new SysErrorRsp("0004", "用户名不存在!").toJsonString();
        }

        // 用户名存在时判断密码是否正确
        if (!userInfo.get("passwd").equalsIgnoreCase(passwd)) {
            return new SysErrorRsp("0004", "密码不正确!").toJsonString();
        }

        // 都正常时
        JSONObject response = new JSONObject();
        response.put("retcode", "0000");
        response.put("retmsg", "");
        response.put("uname", userInfo.get("uname"));
        response.put("uid", uid);
        return response.toJSONString();
    }

    /**
     * 修改密码.
     */
    @Transactional(rollbackFor = Exception.class)
    public String chgPasswd(JSONObject requestData) throws Exception {

        String uid = requestData.getString("uid");
        String oldPasswd = requestData.getString("oldpasswd");
        String newPasswd1 = requestData.getString("newpasswd1");
        String newPasswd2 = requestData.getString("newpasswd2");

        // 新密码是否相同
        if (!newPasswd1.equalsIgnoreCase(newPasswd2)) {
            return new SysErrorRsp("0004", "两次新密码不一致！").toJsonString();
        }

        // 判断新旧密码是否一致
        if (oldPasswd.equalsIgnoreCase(newPasswd1)) {
            return new SysErrorRsp("0004", "新旧密码一致！").toJsonString();
        }

        // 旧密码是否正确
        Map<String, String> userInfo = this.portalRepository.getUserInfo(uid);
        if (!userInfo.get("passwd").equalsIgnoreCase(oldPasswd)) {
            return new SysErrorRsp("0004", "旧密码不正确！").toJsonString();
        }

        // 更新密码
        Map<String, String> param = new HashMap<>();
        param.put("uid", uid);
        param.put("passwd", newPasswd1);
        this.portalRepository.updPasswd(param);

        return new SysErrorRsp("0000", "修改成功！").toJsonString();
    }

    /**
     * 获取预约数量.
     *
     * @return
     */
    public Map<String, Object> quantityInquiry() throws Exception {

        Map<String, Object> data = new HashMap<>();

        // 获取车驾管业务预约数量
        //Map<String, Object> d1 = this.getTotal1();
        //data.putAll(d1);

        // 获取违法业务预约数量
        //Map<String, Object> d2 = this.getTotal2();
        //data.putAll(d2);

        data.put("dailyQuantity", portalRepository.getDailyQuantity(DateTimeUtil.getCurrentDate()));
        data.put("allQuantity", portalRepository.getAllQuantity());
        data.put("numbering", portalRepository.getNumbering(DateTimeUtil.getCurrentDate()));
        data.put("allNumbering", portalRepository.getAllNumbering());
        data.put("cancel", portalRepository.getCancel(DateTimeUtil.getCurrentDate()));
        data.put("allCancel", portalRepository.getAllCancel());
        data.put("overtime", portalRepository.getOvertime(DateTimeUtil.getCurrentDate()));
        data.put("allOvertime", portalRepository.getAllOvertime());
        log.info("取得的预约数量信息为:{}", data);

        // 返回
        return data;
    }

    /**
     * 获取车驾管业务未来6天的预约信息.
     */
    private Map<String, Object> getTotal1() throws Exception {

        Map<String, Object> data = new HashMap<>();

        // 当天日期
        String currentDay = DateTimeUtil.getCurrentDate();
        List<String> dateList = this.getFutureDays();

        // 查询预约人数
        Map<String, String> param = new HashMap<>();
        param.put("today", currentDay);
        // 车驾管业务
        param.put("yylx", "01");
        // 查询车驾管业务预约人数
        List<Map<String, Object>> yyCntList = this.portalRepository.getCnt(param);

        boolean flag = false;
        for (int i = 0; i < dateList.size(); i++) {
            String key = "cnt1" + String.valueOf(i);
            String key2 = "date1" + String.valueOf(i);

            if (dateList.get(i).equals(DateTimeUtil.getCurrentDate())) {
                data.put(key2, "当日人数");
            } else {
                String dt = dateList.get(i).substring(4, 6) + "/" + dateList.get(i).substring(6);
                data.put(key2, dt + "人数");
            }

            for (Map<String, Object> yycnt : yyCntList) {
                if (yycnt.get("yyrq").equals(dateList.get(i))) {

                    int cnt = Integer.parseInt(String.valueOf(yycnt.get("cnt")));
                    System.out.println(cnt);

                    data.put(key, cnt);
                    flag = true;
                }
            }

            // 没有预约人数时
            if (!flag) {
                data.put(key, "0");
            }

            flag = false;

            if (i > 6) {
                break;
            }
        }

        // 返回
        return data;
    }

    /**
     * 获取违法业务未来6天的预约信息.
     */
    private Map<String, Object> getTotal2() throws Exception {

        Map<String, Object> data = new HashMap<>();

        // 当天日期
        String currentDay = DateTimeUtil.getCurrentDate();
        List<String> dateList = this.getFutureDays();

        // 查询预约人数
        Map<String, String> param = new HashMap<>();
        param.put("today", currentDay);
        // 违法业务
        param.put("yylx", "02");
        // 查询车驾管业务预约人数
        List<Map<String, Object>> yyCntList = this.portalRepository.getCnt(param);

        boolean flag = false;
        for (int i = 0; i < dateList.size(); i++) {
            String key = "cnt2" + String.valueOf(i);
            String key2 = "date2" + String.valueOf(i);

            if (dateList.get(i).equals(DateTimeUtil.getCurrentDate())) {
                data.put(key2, "当日人数");
            } else {
                String dt = dateList.get(i).substring(4, 6) + "/" + dateList.get(i).substring(6);
                data.put(key2, dt + "人数");
            }

            for (Map<String, Object> yycnt : yyCntList) {
                if (yycnt.get("yyrq").equals(dateList.get(i))) {

                    int cnt = Integer.parseInt(String.valueOf(yycnt.get("cnt")));
                    System.out.println(cnt);

                    data.put(key, cnt);
                    flag = true;
                }
            }

            // 没有预约人数时
            if (!flag) {
                data.put(key, "0");
            }

            flag = false;

            if (i > 6) {
                break;
            }
        }

        // 返回
        return data;
    }

    /**
     * 获取未来6天工作日的列表.
     */
    private List<String> getFutureDays() throws Exception {

        // 当天日期
        String currentDay = DateTimeUtil.getCurrentDate();

        // 获取未来6天的日期
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i == 0) {
                // 不是节假日时
                if (!this.holidayService.isHoliday(currentDay)) {
                    dateList.add(currentDay);
                }
            } else {
                String date = DateTimeUtil.stepDays(currentDay, i);
                if (!this.holidayService.isHoliday(date)) {
                    dateList.add(date);
                }
            }
        }

        return dateList;
    }
}
