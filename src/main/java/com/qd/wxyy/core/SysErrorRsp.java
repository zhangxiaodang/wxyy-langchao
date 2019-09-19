package com.qd.wxyy.core;

import com.alibaba.fastjson.JSONObject;

public class SysErrorRsp {

    private String retcode;

    private String retmsg;

    public SysErrorRsp(String retmsg) {
        this("0004", retmsg);
    }

    public SysErrorRsp(String retcode, String retmsg) {
        this.retcode = retcode;
        this.retmsg = retmsg;
    }

    /**
     * 转换为String型的json字符串.
     *
     * @return 字符串.
     */
    public String toJsonString() {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("retcode", this.retcode);
        jsonObject.put("retmsg", this.retmsg);

        return jsonObject.toJSONString();
    }

}
