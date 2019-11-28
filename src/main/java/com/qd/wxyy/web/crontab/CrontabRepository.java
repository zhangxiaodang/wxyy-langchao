package com.qd.wxyy.web.crontab;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CrontabRepository {

    /**
     * 清空验证码表.
     */
    void truncateYzm();

    /**
     * 更新预约状态.
     */
    void updYyzt(Map<String, String> param);

    /**
     * 获取还有30分钟就到期的列表.
     */
    List<Map<String, String>> getOrderList(Map<String, String> param);
}
