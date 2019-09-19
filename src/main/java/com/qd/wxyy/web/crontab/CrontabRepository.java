package com.qd.wxyy.web.crontab;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
}
