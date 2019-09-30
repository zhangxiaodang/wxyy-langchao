package com.qd.wxyy.wx.order;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface OrderRepository {

    /**
     * 添加短信验证.
     */
    void addCode(Map<String, String> param);

    /**
     * 修改短信验证.
     */
    void updateCode(Map<String, String> param);

    /**
     * 校验是否存在该用的的验证码.
     */
    int openidQuery(@Param("openid")String openid);

    /**
     * 获取业务列表.
     */
    List<Map<String, Object>> getBusiList();

    /**
     * 获取指定业务的业务数据（包含业务id，业务名称，业务提示信息）.
     */
    Map<String, Object> busiSelectEnd(@Param("busiid")String busiid);

    /**
     * 获取指定业务的业务数据（包含业务id，业务名称，业务提示信息）.
     */
    Map<String, Object> planQuery(@Param("timeid")String timeid);

    /**
     * 获取上次短信信息.
     */
    Map<String, String> getSmsInfo(Map<String, String> param);

    /**
     * 获取当日预约数量.
     */
    Map<String, String> dailyQuantity();

    /**
     * 查询三个月内爽约次数.
     */
    int getYycs(Map<String, String> param);

    /**
     * 根据openid获取用户信息.
     */
    Map<String, String> getWxUserInfo(String openid);
}
