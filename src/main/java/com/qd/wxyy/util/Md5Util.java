package com.qd.wxyy.util;

import java.security.MessageDigest;

/**
 * <p>
 * Md5加密格式类.
 *
 * @author 张孝党 2019/07/16.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/16. 张明亮 创建.
 */
public class Md5Util {

    private static final String hexDigIts[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * MD5加密
     *
     * @param origin      字符
     */
    public static String encode(String origin) {

        // 默认utf-8
        return encode(origin, "utf-8");
    }

    /**
     * MD5加密
     *
     * @param origin      字符
     * @param charsetname 编码
     */
    public static String encode(String origin, String charsetname) {
        String resultString = "";
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null == charsetname || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception e) {
        }
        return resultString;
    }


    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

}


