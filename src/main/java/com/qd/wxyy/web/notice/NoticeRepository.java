package com.qd.wxyy.web.notice;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface NoticeRepository {

    /**
     * 取得提示信息.
     */
    String getMsg();

    /**
     * 保存提示信息.
     */
    void updMsg(String content);

    /**
     * 指定的日期是否为节假日.
     */
    int isHoliday(String rq);

    /**
     * 获取指定日期未取消的预约流水.
     */
    List<Map<String, String>> getYyls(String rq);

    /**
     * 删除指定日期的预约流水.
     */
    void delYyls(String rq);
}
