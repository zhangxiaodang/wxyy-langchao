package com.qd.wxyy.wx.order;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping(value = "/wx")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 业务预约主页.
     */
//    @RequestMapping(value = "/index")
//    public String index(HttpServletRequest request, Model model) throws Exception {
//
//        String authCode = request.getParameter("code");
//        log.info("微信获取的code:" + authCode);
//
//        String openid = orderService.getOpenId(authCode);
//        log.info("获取的openid为{}", openid);
//        //String openid = "123";
//        model.addAttribute("openid", openid);
//
//        // 黄岛交警业务预约主页
//        return "wx/wchatorder";
//    }

    /**
     * 跳转到信息输入页面.
     */
    @RequestMapping(value = "/infoinput")
    public String infoInput(HttpServletRequest request, Model model) throws Exception {

        String authCode = request.getParameter("code");
        log.info("微信获取的code:" + authCode);

        String openid = orderService.getOpenId(authCode);
        log.info("获取的openid为{}", openid);
        //String openid = "o2bZjwYzyqVCcIKlIMyzvM-PeZeQ";

        // 获取上次预约成功后保存的手机号、姓名、身份证号
        String userInfo = this.orderService.getWxUserInfo(openid);
        if(!userInfo.equals("")) {
            model.addAttribute("yyxm", JSONObject.parseObject(userInfo).getString("yyxm"));
            model.addAttribute("yyid", JSONObject.parseObject(userInfo).getString("yyid"));
            model.addAttribute("yysjh", JSONObject.parseObject(userInfo).getString("yysjh"));
        } else {
            model.addAttribute("yyxm", "");
            model.addAttribute("yyid", "");
            model.addAttribute("yysjh", "");
        }

        model.addAttribute("openid", openid);

        // 浪潮返回业务预约主页
        return "wx/infoinput";
    }

    /**
     * 业务选择画面，并返回业务数据
     */
    @RequestMapping(value = "/busiselect")
    public String busiSelect(Model model) throws Exception {
        model.addAttribute("busilist", orderService.getBusiList());
        return "wx/busiselect";
    }


    /**
     * 获取指定业务的业务数据（包含业务id，业务名称，业务提示信息）
     */
    @RequestMapping(value = "/busiselectend", produces = "application/json;charset=UTF-8")
    public String busiSelectEnd(String busiid, Model model) throws Exception {
        model.addAttribute("business", orderService.busiSelectEnd(busiid));
        return "wx/infoinput";
    }

    /**
     * 选择时间后返回信息输入画面
     */
    @RequestMapping(value = "/timeselectend", produces = "application/json;charset=UTF-8")
    public String timeSelectEnd(String timeid, String date, Model model) throws Exception {
        model.addAttribute("time", orderService.planQuery(timeid, date));
        return "wx/infoinput";
    }

    /**
     * 预约成功，显示预约结果画面
     */
    @RequestMapping(value = "/orderresult")
    public String orderResult(Model model) throws Exception {
        return "wx/orderresult";
    }

    /**
     * 获取验证码.
     */
    @RequestMapping(value = "/getverify", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getVerify(@RequestBody String requestData) throws Exception {
        log.info("获取验证码接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.orderService.sendSms(requestJsonData);
        log.info("获取验证码返回的结果为{}", response);
        return response;
    }

    /**
     * 获取当日预约数量
     */
    @RequestMapping(value = "/dailyquantity", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String dailyQuantity(@RequestBody String request) throws Exception {

        log.info("获取验证码接收到的参数为：{}", request);
        JSONObject requestJsonData = JSONObject.parseObject(request);

        Object response = this.orderService.dailyQuantity();
        log.info("获取验证码返回的结果为{}", response);
        return "";
    }

}
