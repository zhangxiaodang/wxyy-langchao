package com.qd.wxyy.wx.device;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备端取号.
 *
 * @author 张孝党 2019/07/18.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/18 张孝党 创建.
 */
@Slf4j
@RestController
@RequestMapping(value = "/wx")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 取号接口.
     */
    @RequestMapping(value = "/checkorder", produces = "application/json;charset=UTF-8")
    public String checkOrder(@RequestBody String requestData) throws Exception {

        log.info("checkOrder接收到的参数为：{}", requestData);
        JSONObject requestJsonData = JSONObject.parseObject(requestData);

        String response = this.deviceService.getOrder(requestJsonData);
        log.info("取号返回的数据为：{}", response);

        // 返回
        return response;
    }
}
