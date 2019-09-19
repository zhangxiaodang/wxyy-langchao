package com.qd.wxyy.web.ordertime;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TimeRepository {

    /**
     * 获取工作时间设置.
     */
    Map<String, String> getGzsj();

    /**
     * 获取预约时间列表.
     */
    List<Map<String, String>> getYysj();

    /**
     * 删除.
     */
    void delData(String id);

    /**
     * 删除预约表.
     */
    void delYyls(Map<String, String> param);

    /**
     * 更新预约时间表.
     */
    void updGzsj(Map<String, String> param);

    /**
     * 清空预约时间表
     */
    void truncateYysj();

    /**
     * 新增预约计划.
     */
    void insYyjh(Map<String, String> param);
}
