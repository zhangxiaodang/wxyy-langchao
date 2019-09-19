package com.qd.wxyy.web.query;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.wx.query.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/web/order")
public class OrderQueryControl {
    @Autowired
    OrderQueryService queryService;
    /**
     * 预约业务管理.
     */
    @RequestMapping(value = "/index")
    public String index(Map<String, String> map) throws Exception {

        return "web/analyse/order";
    }

    /**
     * 业务查询
     */
    @RequestMapping(value = "/query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String busiList(@RequestBody String requestData) throws Exception {

        log.info("预约查询接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.queryService.queryData(requestJsonData);
        log.info("预约查询结果为{}", response);

        return response;
    }
}
