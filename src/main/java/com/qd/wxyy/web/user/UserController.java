package com.qd.wxyy.web.user;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 用户管理Controller.
 *
 * @author 张孝党 2019/07/16.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/16 张孝党 创建.
 */

@Slf4j
@Controller
@RequestMapping(value = "/web/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户管理首页.
     */
    @RequestMapping(value = "/index")
    public String index(Map<String, Object> resultMap) throws Exception {
        return "web/user/user";
    }

    /**
     * 查询用户列表
     */
    @RequestMapping(value = "/query", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String userList(@RequestBody String requestData) throws Exception {

        log.info("userList接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.userService.getUserList(requestJsonData);
        log.info("查询结果为{}", response);

        return response;
    }

    /**
     * 增加用户.
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String add(@RequestBody String requestData) throws Exception{

        log.info("add接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.userService.addUser(requestJsonData);
        log.info("增加用户结果为{}", response);
        return response;
    }

    /**
     * 修改用户.
     */
    @RequestMapping(value = "/upd", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String upd(@RequestBody String requestData) throws Exception{

        log.info("upd接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.userService.updUser(requestJsonData);
        log.info("修改用户结果为{}", response);
        return response;
    }

    /**
     * 删除用户.
     */
    @RequestMapping(value = "/del", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String del(@RequestBody String requestData) throws Exception{

        log.info("del接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.userService.delUser(requestJsonData);
        log.info("删除用户结果为{}", response);
        return response;
    }
}
