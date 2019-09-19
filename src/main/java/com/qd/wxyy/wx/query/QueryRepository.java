package com.qd.wxyy.wx.query;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface QueryRepository {

    /**
     * 查询预约数据.
     */
    List<Map<String, String>> queryData(String openid);

    /**
     * 更新为取消状态.
     */
    void updCancel(String id);
}
