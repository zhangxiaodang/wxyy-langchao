package com.qd.wxyy.web.portal;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PortalRepository {

    /**
     * 获取用户信息.
     */
    Map<String, String> getUserInfo(String uid);

    /**
     * 更新密码.
     */
    void updPasswd(Map<String, String> param);

    /**
     * 获取当日预约人数.
     */
    int getDailyQuantity(String yyrq);

    /**
     * 获取历史所有预约人数.
     */
    int getAllQuantity();

    /**
     * 获取当日取号人数.
     */
    int getNumbering(String yyrq);

    /**
     * 获取历史所有取号人数.
     */
    int getAllNumbering();

    /**
     * 获取当日取消人数.
     */
    int getCancel(String yyrq);

    /**
     * 获取历史所有取消人数.
     */
    int getAllCancel();

    /**
     * 获取当日超时人数.
     */
    int getOvertime(String yyrq);

    /**
     * 获取历史所有超时人数.
     */
    int getAllOvertime();

    /**
     * 获取每天的预约数量.
     */
    List<Map<String, Object>> getCnt(Map<String, String> param);
}
