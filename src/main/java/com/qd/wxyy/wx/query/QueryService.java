package com.qd.wxyy.wx.query;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    /**
     * 查询预约数据.
     */
    public List<Map<String, String>> queryData(String openid) throws Exception {

        log.info("微信用户的openid为{}", openid);

        // 查询预约历史
        List<Map<String, String>> dataList = this.queryRepository.queryData(openid);
        log.info("查询出的预约数据为{}", dataList);

        return dataList;
    }

    /**
     * 取消.
     */
    public String cancel(JSONObject requestData) throws Exception {

        // 更新为取消状态
        this.queryRepository.updCancel(requestData.getString("orderid"));

        JSONObject response = new JSONObject();
        response.put("retcode", "0000");
        response.put("retmsg", "取消成功");
        response.put("orderlist", this.queryData(requestData.getString("openid")));

        return response.toJSONString();
    }
}
