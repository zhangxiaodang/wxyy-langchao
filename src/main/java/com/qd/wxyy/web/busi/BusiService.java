package com.qd.wxyy.web.busi;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import com.qd.wxyy.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BusiService {
    @Autowired
    private BusiRepository busiRepository;

    /**
     * 根据条件查询业务一览.
     */
    public String getBusiList(JSONObject requestData) {

        String id = "";
        String uname = "";
        if (requestData != null) {
            id = requestData.getString("uid");
            uname = requestData.getString("uname");
        }
        // 查询参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("uname", uname);
        //查询所有的机构信息（包含父业务和子业务）
        List<Map<String, Object>> lstData = busiRepository.getBusiList(paramMap);
        // 构建业务树
        HashMap<String, Object> busilist = new HashMap<String, Object>();
        this.getJgTree(lstData, "0", busilist);
        log.info("取得的业务信息为:{}", busilist.get("busilist"));
        JSONObject result = new JSONObject();
        result.put("retcode", "0000");
        result.put("retmsg", "成功");
        result.put("busilist", busilist.get("busilist"));
        return result.toJSONString();
    }

    /**
     * 业务新增.
     */
    @Transactional(rollbackFor = Exception.class)
    public String addBusi(JSONObject requestData) {
        int nameQuery = busiRepository.nameQuery(requestData.getString("uname"));
        if (nameQuery == 0) {
            // 参数
            Map<String, String> paramMap = this.decoupling(requestData);
            paramMap.put("id", CommonUtil.getUUid());
            this.busiRepository.addBusi(paramMap);
            // 返回
            return new SysErrorRsp("0000", "成功").toJsonString();
        } else {
            return new SysErrorRsp("0004", "业务名称不可相同，请修改后重试！").toJsonString();
        }
    }

    /**
     * 业务删除.
     */
    @Transactional(rollbackFor = Exception.class)
    public String delBusi(JSONObject requestData) {
        int sublevel = busiRepository.sublevel(requestData.getString("id"));
        if (sublevel == 0) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("id", requestData.getString("id"));
            this.busiRepository.delBusi(requestData.getString("id"));
            // 返回
            return new SysErrorRsp("0000", "删除成功").toJsonString();
        } else {
            return new SysErrorRsp("0004", "该业务下存在子业务，不可进行删除操作！").toJsonString();
        }


    }

    /**
     * 业务修改
     */
    @Transactional(rollbackFor = Exception.class)
    public String updBusi(JSONObject requestData) {
        Map<String, Object> paramMaps = new HashMap<>();
        paramMaps.put("uname", requestData.getString("uname").trim());
        List<Map<String, Object>> busi = busiRepository.getBusiList(paramMaps);
        if (busi.size() == 0 || busi.get(0).get("id").equals(requestData.getString("id"))) {
            // 参数
            Map<String, String> paramMap = this.decoupling(requestData);
            paramMap.put("id", requestData.getString("id"));
            this.busiRepository.updBusi(paramMap);
            // 返回
            return new SysErrorRsp("0000", "成功").toJsonString();
        } else {
            return new SysErrorRsp("0004", "业务名称不可相同，请修改后重试！").toJsonString();
        }

    }

    /**
     * 修改和添加  参数解析.
     */
    public Map<String, String> decoupling(JSONObject requestData) {
        // 参数
        Map<String, String> paramMap = new HashMap<>();
        if (requestData.getString("fjid").equals("")) {
            paramMap.put("fjid", "0");
        } else {
            paramMap.put("fjid", requestData.getString("fjid"));
        }
        paramMap.put("types", requestData.getString("types"));
        paramMap.put("sort", requestData.getString("sort"));
        paramMap.put("content", requestData.getString("content"));
        paramMap.put("uname", requestData.getString("uname").trim());
        return paramMap;
    }

    /**
     * 构建业务树.
     *
     * @param lstData 原始数据.
     * @param pid     上级节点ID.
     * @return 业务树.
     */
    private void getJgTree(List<Map<String, Object>> lstData, String pid, Map<String, Object> treeData) {

        List<Map<String, Object>> childrenData = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : lstData) {
            if (map.get("fjid").toString().equals(pid)) {
                childrenData.add(map);
            }
        }

        if (childrenData.size() != 0) {
            treeData.put("busilist", childrenData);
            for (Map<String, Object> m : childrenData) {
                getJgTree(lstData, m.get("id").toString(), m);
            }
        }
    }
}
