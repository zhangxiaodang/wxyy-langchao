package com.qd.wxyy.wx.device;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface DeviceRepository {

    /**
     * 获取取号信息.
     */
    Map<String, String> getOrderInfo(Map<String, String> param);

    /**
     * 更新取号信息.
     */
    void updOrderInfo(Map<String, String> map);

    /**
     * 更新过期标志.
     */
    void updTimeOut(String id);
}
