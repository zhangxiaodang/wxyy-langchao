package com.qd.wxyy.wx.reservation;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ReservationRepository {

    /**
     * 添加预约.
     */
    void addReservation(Map<String, String> param);

    /**
     * 假日日期查询.
     */
    int holidayQuery(@Param("date")String date);

    /**
     * 根据日期查询是否预约.
     */
    List<Map<String, String>> subscribe(Map<String, String> param);

    /**
     * 手机验证码验证.
     */
    Map<String, String> getCode(@Param("openid")String openid);

    /**
     * 手机验证码删除.
     */
    void delCode(@Param("id")String id);

    /**
     * 该身份证号是否预约过.
     */
    int checkSfyy(Map<String, String> param);
}
