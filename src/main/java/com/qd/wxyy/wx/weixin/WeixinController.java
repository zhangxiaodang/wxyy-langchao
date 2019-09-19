package com.qd.wxyy.wx.weixin;

import com.qd.wxyy.util.WeixinCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信开发配置类.
 *
 * @author 张孝党 2019/07/16.
 * @version V1.00.
 * <p>
 * 更新履历： V1.00 2019/07/16 张孝党 创建.
 */
@Slf4j
@Controller
@RequestMapping(value = "/wxyy")
public class WeixinController {

    /**
     * 随机字符串.
     */
    private static final String ECHOSTR = "echostr";

    @RequestMapping(value = "/authentication")
    @ResponseBody
    public String authentication(HttpServletRequest request) {

        // 随机字符串
        String echostr = request.getParameter(ECHOSTR);
        log.info("取得的微信随机字符串为：" + echostr);

        WeixinCommonUtil weixinCommonUtil = new WeixinCommonUtil();
        if (weixinCommonUtil.checkSignature(request) && echostr != null) {
            return echostr;
        } else {
            return "error";
        }
    }
}
