package com.qd.wxyy.web.user;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import com.qd.wxyy.util.CommonUtil;
import com.qd.wxyy.util.Md5Util;
import com.qd.wxyy.web.portal.PortalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Service.
 *
 * @author 张孝党 2019/07/16.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/16 张孝党 创建.
 */
@Slf4j
@Service
public class UserService {

    // 初始密码
    private final String INIT_PASSWD = "123456";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortalRepository portalRepository;

    /**
     * 根据条件查询用户一览.
     */
    public String getUserList(JSONObject requestData) {

        String uid = "";
        String uname = "";
        int startindex = 0;
        int pagesize = 10;

        if (requestData != null) {
            uid = requestData.getString("uid");
            uname = requestData.getString("uname");
            startindex = requestData.getIntValue("startindex");
            pagesize = requestData.getIntValue("pagesize");
        }

        // 查询参数
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("uid", uid);
        paramMap.put("uname", uname);
        paramMap.put("startindex", startindex);
        paramMap.put("pagesize", pagesize);

        // 查询用户条数
        int userListCnt = this.userRepository.getUserListCnt(paramMap);
        log.info("共有[{}]条数据.", userListCnt);

        // 查询用户列表
        List<Map<String, Object>> userList = this.userRepository.getUserList(paramMap);
        log.info("取得的用户信息为:{}", userList);

        JSONObject result = new JSONObject();
        result.put("retcode", "0000");
        result.put("retmsg", "成功");
        result.put("cnt", userListCnt);
        result.put("userinfo", userList);

        return result.toJSONString();
    }

    /**
     * 新增用户.
     */
    @Transactional(rollbackFor = Exception.class)
    public String addUser(JSONObject requestData) {

        String uid = requestData.getString("uid");
        String uname = requestData.getString("uname");

        Map<String, String> userInfo = this.portalRepository.getUserInfo(uid);
        if(userInfo != null) {
            return new SysErrorRsp("0004", "用户名已存在！").toJsonString();
        }

        // 新增用户
        // 参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("id", CommonUtil.getUUid());
        paramMap.put("uid", uid);
        paramMap.put("uname", uname);
        paramMap.put("passwd", Md5Util.encode(INIT_PASSWD));
        this.userRepository.add(paramMap);

        // 返回
        return new SysErrorRsp("0000", "成功").toJsonString();
    }

    /**
     * 删除用户.
     */
    @Transactional(rollbackFor = Exception.class)
    public String delUser(JSONObject requestData) {

        // 删除用户
        this.userRepository.del(requestData.getString("uid"));

        // 返回
        return new SysErrorRsp("0000", "成功").toJsonString();
    }

    /**
     * 更新用户,只能修改姓名
     */
    @Transactional(rollbackFor = Exception.class)
    public String updUser(JSONObject requestData) {

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("uid", requestData.getString("uid"));
        paramMap.put("uname", requestData.getString("uname"));

        this.userRepository.updUser(paramMap);

        // 返回
        return new SysErrorRsp("0000", "成功").toJsonString();
    }
}
