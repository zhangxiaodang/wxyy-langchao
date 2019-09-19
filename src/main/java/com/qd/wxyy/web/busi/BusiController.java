package com.qd.wxyy.web.busi;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 预约业务管理.
 *
 * @author 张孝党 2019/07/16.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/16 张孝党 创建.
 */
@Slf4j
@Controller
@RequestMapping(value = "/web/busi")
public class BusiController {

    @Autowired
    private BusiService busiService;

    /**
     * 预约业务管理.
     */
    @RequestMapping(value = "/index")
    public String index(Map<String, String> map) {

        return "web/order/busi";
    }

    /**
     * 业务查询
     */
    @RequestMapping(value = "/query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String busiList(@RequestBody String requestData) throws Exception {

        log.info("业务查询接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.busiService.getBusiList(requestJsonData);
        log.info("业务查询结果为{}", response);

        return response;
    }

    /**
     * 业务增加.
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String add(@RequestBody String requestData) throws Exception{

        log.info("业务增加接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.busiService.addBusi(requestJsonData);
        log.info("业务增加结果为{}", response);
        return response;
    }

    /**
     * 业务删除.
     */
    @RequestMapping(value = "/del", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String del(@RequestBody String requestData) throws Exception{

        log.info("业务删除接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.busiService.delBusi(requestJsonData);
        log.info("业务删除结果为{}", response);
        return response;
    }

    /**
     * 业务修改.
     */
    @RequestMapping(value = "/upd", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String upd(@RequestBody String requestData) throws Exception{

        log.info("业务修改接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.busiService.updBusi(requestJsonData);
        log.info("业务修改结果为{}", response);
        return response;
    }
}
