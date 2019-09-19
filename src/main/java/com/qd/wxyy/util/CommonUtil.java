package com.qd.wxyy.util;

import java.util.Random;
import java.util.UUID;

/**
 * Copyright(C) ShanDongYinFang 2019.
 * 公共方法类.
 *
 * @author 张孝党 2019/06/03.
 * @version V0.0.2.
 * <p>
 * 更新履历： V0.0.1 2019/06/03 张孝党 创建.
 */
public class CommonUtil {

    /**
     * 获取UUID.
     */
    public static String getUUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成指定长度的验证码.
     */
    public static String getSmsCode(int iLen) {

        String code = "";
        Random random = new Random();

        for(int i=0;i<iLen;i++) {
            int r = random.nextInt(10);
            code = code + r;
        }

        return code;
    }


    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }
}
