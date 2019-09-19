package com.qd.wxyy.web.notice;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 取消预约消息管理..
 *
 * @author 张孝党 2019/07/25.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/25 张孝党 创建.
 */

@Slf4j
@Controller
@RequestMapping(value = "/web/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 主页.
     */
    @RequestMapping(value = "/cancel")
    public String index() {

        return "web/order/cancel";
    }

    /**
     * 查询.
     */
    @RequestMapping(value = "/query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getMsg() throws Exception {

        String response = this.noticeService.getMsg();
        log.info("查询结果为{}", response);
        return response;
    }

    /**
     * 保存.
     */
    @RequestMapping(value = "/edit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String edit(@RequestBody String requestData) throws Exception {

        log.info("edit接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.noticeService.getEdit(requestJsonData);
        log.info("更新结果为{}", response);
        return response;
    }
}
