package com.qd.wxyy.web.busi;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BusiRepository {

    /**
     * 查询该业务下是否存在子业务.
     */
    int sublevel(@Param("fjid") String fjid);

    /**
     * 查询业务名称是否相同.
     */
    int nameQuery(@Param("uname") String uname);

    /**
     * 获取业务列表.
     */
    List<Map<String, Object>> getBusiList(Map<String, Object> param);

    /**
     * 获取单条业务数据.
     */
    List<Map<String, Object>> getBusi(Map<String, Object> param);

    /**
     * 新增业务.
     */
    void addBusi(Map<String, String> param);

    /**
     * 删除业务.
     */
    void delBusi(@Param("id")String id);

    /**
     * 更新业务.
     */
    void updBusi(Map<String, String> param);
}
