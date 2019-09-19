package com.qd.wxyy.web.holiday;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 登录及修改密码
 *
 * @author 张孝党 2019/07/16.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/16 张孝党 创建.
 */
@Slf4j
@Controller
@RequestMapping(value = "/web/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    /**
     * 节假日管理主页.
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("year", DateTimeUtil.getCurrentYear());
        return "web/order/holiday";
    }

    /**
     * 查询节假日.
     */
    @RequestMapping(value = "/query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryData(@RequestBody String requestData) throws Exception {

        log.info("queryData接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        // 查询数据
        String response = this.holidayService.queryHolidayList(requestJsonData);
        log.info("查询结果为{}", response);
        return response;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String add(@RequestBody String requestData) throws Exception {

        log.info("add接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        // 新增数据
        String response = this.holidayService.addData(requestJsonData);
        log.info("新增结果为{}", response);
        return response;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/del", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String del(@RequestBody String requestData) throws Exception {

        log.info("del接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        // 新增数据
        String response = this.holidayService.delData(requestJsonData);
        log.info("删除结果为{}", response);
        return response;
    }


}
