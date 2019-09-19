package com.qd.wxyy.wx.query;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.wx.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 预约查询.
 *
 * @author 姜国莹 2019/07/18.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/18 姜国莹 创建.
 */
@Slf4j
@Controller
@RequestMapping(value = "/wx")
public class QueryController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private QueryService queryService;

    /**
     * 预约查询主页.
     */
    @RequestMapping(value = "/query")
    public String index(HttpServletRequest request, Model model) throws Exception {

        String authCode = request.getParameter("code");
        log.info("微信获取的code:" + authCode);

        String openid = orderService.getOpenId(authCode);
        log.info("获取的openid为{}", openid);
        //String openid = "123";

        // 查询数据
        List<Map<String, String>> dataList = queryService.queryData(openid);

        model.addAttribute("openid", openid);
        model.addAttribute("orderlist", dataList);

        // 返回预约查询页面
        return "wx/orderquery";
    }

    /**
     *取消.
     */
    @RequestMapping(value = "/ordercancel", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String cancel(@RequestBody String requestData) throws Exception {

        log.info("cancel接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.queryService.cancel(requestJsonData);
        log.info("取消结果为{}", response);

        // 返回
        return response;
    }
}
