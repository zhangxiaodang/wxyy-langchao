package com.qd.wxyy.core;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理.
 *
 * @author 张孝党 2019/07/14.
 * @version V1.00.
 * <p>
 * 更新履历： V1.00 2019/07/14 张孝党 创建.
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理.
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String sysExceptionHandler(Exception ex) {

        log.error("全局异常信息---------->{}", ex.getMessage());
        // 组织异常报文
        JSONObject json = new JSONObject();
        json.put("retcode", "9999");
        json.put("retmsg", ex.getMessage());

        // 返回
        return json.toJSONString();
    }
}
