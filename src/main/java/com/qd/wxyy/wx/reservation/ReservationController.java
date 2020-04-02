package com.qd.wxyy.wx.reservation;

import com.alibaba.fastjson.JSONObject;
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
 * 业务预约.
 *
 * @author 张明亮 2019/07/17.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/17 张明亮 创建.
 */
@Slf4j
@Controller
@RequestMapping(value = "/wx")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    /**
     * 提交预约数据.
     */
    @RequestMapping(value = "/orderconfirm", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String orderconfirm(@RequestBody String requestData) throws Exception {

        log.info("业务预约接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.reservationService.reservationAdd(requestJsonData);
        log.info("业务预约结果为{}", response);
        return response;
    }

    /**
     * 查询未来8天的时间.
     */
    @RequestMapping(value = "/timeselect", produces = "application/json;charset=UTF-8")
    public String timeselect(Model model, String busiid) throws Exception {
        List<Map<String, Object>> datelist = this.reservationService.timeSelect(busiid);
        log.info("预约时间查询结果为{}", datelist);
        model.addAttribute("datelist", datelist);
        return "wx/timeselect";
    }

    /**
     * 获取该日期的预约时间段信息.
     */
    @RequestMapping(value = "/getordertime", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getordertime(@RequestBody String requestData) throws Exception {

        log.info("预约时间查询接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.reservationService.getordertime(requestJsonData);
        log.info("预约时间查询结果为{}", response);
        return response;
    }


}
