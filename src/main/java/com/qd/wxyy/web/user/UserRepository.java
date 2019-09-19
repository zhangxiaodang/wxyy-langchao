package com.qd.wxyy.web.user;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户管理Repository.
 *
 * @author 张孝党 2019/07/16.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/16 张孝党 创建.
 */
@Mapper
@Repository
public interface UserRepository {

    /**
     * 获取用户列表条数.
     */
    int getUserListCnt(Map<String, Object> param);

    /**
     * 获取用户列表.
     */
    List<Map<String, Object>> getUserList(Map<String, Object> param);

    /**
     * 新增用户.
     */
    void add(Map<String, String> param);

    /**
     * 删除用户.
     */
    void del(String uid);

    /**
     * 更新用户.
     */
    void updUser(Map<String, String> param);
}
