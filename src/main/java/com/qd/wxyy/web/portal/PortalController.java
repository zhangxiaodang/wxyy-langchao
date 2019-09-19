package com.qd.wxyy.web.portal;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.NumberFormat;
import java.util.Map;

/**
 * 登录及修改密码
 *
 * @author 张孝党 2019/07/14.
 * @version V0.0.2.
 * <p>
 * 更新履历： V0.0.1 2019/07/14 张孝党 创建.
 */
@Slf4j
@Controller
@RequestMapping(value = "/web/portal")
public class PortalController {

    @Autowired
    private PortalService portalService;

    /**
     * 登录画面.
     */
    @RequestMapping(value = "")
    public String login(Model model) throws Exception {
        return "web/login";
    }

    /**
     * 退出登录.
     */
    @RequestMapping(value = "/logout", produces = "application/json;charset=UTF-8")
    public String timeselect(Model model) throws Exception {
        return "web/login";
    }

    /**
     * 主页面画面.
     */
    @RequestMapping(value = "/main")
    public String main(Model model) throws Exception {
        Map<String, Object> data = (Map<String, Object>) portalService.quantityInquiry();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        // 车驾管业务
        model.addAttribute("date10", data.get("date10"));
        model.addAttribute("cnt10", data.get("cnt10"));
        model.addAttribute("date11", data.get("date11"));
        model.addAttribute("cnt11", data.get("cnt11"));
        model.addAttribute("date12", data.get("date12"));
        model.addAttribute("cnt12", data.get("cnt12"));
        model.addAttribute("date13", data.get("date13"));
        model.addAttribute("cnt13", data.get("cnt13"));
        model.addAttribute("date14", data.get("date14"));
        model.addAttribute("cnt14", data.get("cnt14"));
        model.addAttribute("date15", data.get("date15"));
        model.addAttribute("cnt15", data.get("cnt15"));

        // 违法业务
        model.addAttribute("date20", data.get("date20"));
        model.addAttribute("cnt20", data.get("cnt20"));
        model.addAttribute("date21", data.get("date21"));
        model.addAttribute("cnt21", data.get("cnt21"));
        model.addAttribute("date22", data.get("date22"));
        model.addAttribute("cnt22", data.get("cnt22"));
        model.addAttribute("date23", data.get("date23"));
        model.addAttribute("cnt23", data.get("cnt23"));
        model.addAttribute("date24", data.get("date24"));
        model.addAttribute("cnt24", data.get("cnt24"));
        model.addAttribute("date25", data.get("date25"));
        model.addAttribute("cnt25", data.get("cnt25"));

        model.addAttribute("dailyQuantity", numberFormat.format(data.get("dailyQuantity")));
        model.addAttribute("allQuantity", numberFormat.format(data.get("allQuantity")));
        model.addAttribute("numbering", numberFormat.format(data.get("numbering")));
        model.addAttribute("allNumbering", numberFormat.format(data.get("allNumbering")));
        model.addAttribute("cancel", numberFormat.format(data.get("cancel")));
        model.addAttribute("allCancel", numberFormat.format(data.get("allCancel")));
        model.addAttribute("overtime", numberFormat.format(data.get("overtime")));
        model.addAttribute("allOvertime", numberFormat.format(data.get("allOvertime")));
        return "web/main";
    }

    /**
     * 登录.
     */
    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String loginVertify(@RequestBody String requestData) throws Exception {

        log.info("loginVertify接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        // 用户名不能为空
        if (requestJsonData.get("uid").toString().isEmpty()) {
            return new SysErrorRsp("用户名不能为空!").toJsonString();
        }

        // 密码不能为空
        if (requestJsonData.get("passwd").toString().isEmpty()) {
            return new SysErrorRsp("密码不能为空!").toJsonString();
        }

        // 验密业务
        String responseData = this.portalService.loginVertify(requestJsonData);
        return responseData;
    }

    /**
     * 修改密码画面.
     */
    @RequestMapping(value = "/password")
    public String password(Model model) throws Exception {
        return "web/user/password";
    }

    /**
     * 修改密码.
     */
    @RequestMapping(value = "/chgpasswd", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String chgPasswd(@RequestBody String requestData) throws Exception {

        log.info("chgPasswd接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String responseData = this.portalService.chgPasswd(requestJsonData);
        return responseData;
    }

}
