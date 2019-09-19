package com.qd.wxyy.web.ordertime;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 预约时间管理.
 *
 * @author 张孝党 2019/07/17.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/17 张孝党 创建.
 */
@Slf4j
@Controller
@RequestMapping(value = "/web/time")
public class TimeController {

    @Autowired
    private TimeService timeService;

    @RequestMapping(value = "/index")
    public String index(Model model) {
        return "web/order/time";
    }

    /**
     * 查询
     */
    @RequestMapping(value = "/query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String query(@RequestBody String requestData) throws Exception {

        log.info("query接收到的参数为：{}", requestData);

        String gzsjData = this.timeService.queryGzsj();
        log.info("查询结果为{}", gzsjData);
        return gzsjData;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/del", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String del(@RequestBody String requestData) throws Exception {

        log.info("del接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        // 删除数据
        String response = this.timeService.delData(requestJsonData);
        log.info("删除结果为{}", response);
        return response;
    }

    /**
     * 更新.
     */
    @RequestMapping(value = "/upd", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String upd(@RequestBody String requestData) throws Exception {

        log.info("upd接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        // 更新数据
        String response = this.timeService.updData(requestJsonData);
        log.info("更新的结果为{}", response);
        return response;
    }
}
