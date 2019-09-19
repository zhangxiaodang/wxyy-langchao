package com.qd.wxyy.web.ordertime;


import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import com.qd.wxyy.util.CommonUtil;
import com.qd.wxyy.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TimeService {

    @Autowired
    private TimeRepository timeRepository;

    /**
     * 取得工作时间数据.
     */
    public String queryGzsj() {

        JSONObject response = new JSONObject();

        Map<String, String> gzsj = this.timeRepository.getGzsj();
        List<Map<String, String>> yysj = this.timeRepository.getYysj();

        response.put("retcode", "0000");
        response.put("retmsg", "成功");
        response.put("gzsj", gzsj);
        response.put("cnt", yysj.size());
        response.put("yysj", yysj);

        // 返回
        return response.toJSONString();
    }

    /**
     * 删除数据.
     */
    @Transactional(rollbackFor = Exception.class)
    public String delData(JSONObject requestData) throws Exception {

        String id = requestData.getString("id");
        String kssj = requestData.getString("kssj").replace(":", "");
        String jssj = requestData.getString("jssj").replace(":", "");

        // 删除预约时间计划表
        this.timeRepository.delData(id);

        // 删除微信预约表中未预约的该时间段流水
        Map<String, String> param = new HashMap<>();
        param.put("kssj", kssj);
        param.put("jssj", jssj);
        param.put("rq", DateTimeUtil.getCurrentDate());
        this.timeRepository.delYyls(param);

        return new SysErrorRsp("0000", "成功").toJsonString();
    }

    /**
     * 更新.
     */
    @Transactional(rollbackFor = Exception.class)
    public String updData(JSONObject requestData) throws Exception {

        // 更新工作时间表
        String a_kssj = CommonUtil.addZeroForNum(requestData.getString("a_kssj").replace(":", ""), 4);
        String a_jssj = CommonUtil.addZeroForNum(requestData.getString("a_jssj").replace(":", ""), 4);
        String p_kssj = CommonUtil.addZeroForNum(requestData.getString("p_kssj").replace(":", ""), 4);
        String p_jssj = CommonUtil.addZeroForNum(requestData.getString("p_jssj").replace(":", ""), 4);
        String jgsj = requestData.getString("jgsj");
        String kyyrs = requestData.getString("kyyrs");
        Map<String, String> param1 = new HashMap<>();
        param1.put("a_kssj", a_kssj);
        param1.put("a_jssj", a_jssj);
        param1.put("p_kssj", p_kssj);
        param1.put("p_jssj", p_jssj);
        param1.put("jgsj", jgsj);
        param1.put("kyyrs", kyyrs);
        this.timeRepository.updGzsj(param1);

        // 计算时间拆分
        List<String> amList = DateTimeUtil.getIntervalTimeList(requestData.getString("a_kssj"), requestData.getString("a_jssj"), Integer.valueOf(jgsj));
        log.info("zhangxd--------------------->{}", amList);
        List<String> pmList = DateTimeUtil.getIntervalTimeList(requestData.getString("p_kssj"), requestData.getString("p_jssj"), Integer.valueOf(jgsj));
        log.info("zhangxd--------------------->{}", pmList);
        // 新增预约时间计划表
        this.updYyjh(amList, pmList);

        // 获取最新的预约计划列表
        JSONObject response = new JSONObject();
        List<Map<String, String>> yysj = this.timeRepository.getYysj();

        response.put("retcode", "0000");
        response.put("retmsg", "成功");
        response.put("cnt", yysj.size());
        response.put("yysj", yysj);

        // 返回
        return response.toJSONString();
    }

    /**
     * 清空原预约计划表
     *
     * @param amList
     * @param pmList
     */
    private void updYyjh(List<String> amList, List<String> pmList) {
        // 清空原有的预约时间计划表
        this.timeRepository.truncateYysj();

        // 上午时间
        for (int i = 0; i < amList.size() - 1; i++) {
            String kssj_tmp = amList.get(i);
            String jssj_tmp = amList.get(i + 1);
            log.info("时间序列：{} ~ {}", kssj_tmp, jssj_tmp);

            Map<String, String> param2 = new HashMap<>();
            param2.put("id", CommonUtil.getUUid());
            param2.put("kssj", kssj_tmp);
            param2.put("jssj", jssj_tmp);
            this.timeRepository.insYyjh(param2);
        }
        log.info("上午时间更新完成..........");

        // 下午时间
        for (int i = 0; i < pmList.size() - 1; i++) {
            String kssj_tmp = pmList.get(i);
            String jssj_tmp = pmList.get(i + 1);
            log.info("时间序列：{} ~ {}", kssj_tmp, jssj_tmp);

            Map<String, String> param2 = new HashMap<>();
            param2.put("id", CommonUtil.getUUid());
            param2.put("kssj", kssj_tmp);
            param2.put("jssj", jssj_tmp);
            this.timeRepository.insYyjh(param2);
        }
        log.info("下午时间更新完成..........");
    }


}
