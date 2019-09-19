package com.qd.wxyy.web.holiday;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface HolidayRepository {

    /**
     * 初始化数据.
     */
    void initData(Map<String, String> param);

    /**
     * 查询条数.
     */
    int queryCnt(Map<String, Object> param);

    /**
     * 查询数据列表.
     */
    List<Map<String,String>> queryList(Map<String, Object> param);

    void addData(Map<String, String> param);

    void delData(String id);

    Map<String, String> checkData(Map<String, String> param);

    /**
     * 指定日期是否是节假日.
     */
    int isHoliday(String targetDate);
}
