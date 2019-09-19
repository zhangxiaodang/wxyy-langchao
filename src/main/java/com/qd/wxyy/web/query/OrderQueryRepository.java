package com.qd.wxyy.web.query;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 预约查询Repository.
 *
 * @author 姜国莹 2019/07/22.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/22 姜国莹 创建.
 */

@Mapper
@Repository
public interface OrderQueryRepository {
    /**
     * 获取预约列表条数.
     */
    int getOrderListCnt(Map<String, Object> param);
    /**
     * 查询预约数据.
     */
    List<Map<String, Object>> getOrderList(Map<String, Object> param);
}
