package com.qd.wxyy.web.query;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderQueryService {
    @Autowired
    private OrderQueryRepository queryRepository;

    /**
     * 查询预约数据.
     */
    public String queryData(JSONObject requestData) throws Exception {

        String phone = "";
        String starttime = "";
        String endtime = "";
        String date = "";
        int startindex = 0;
        int pagesize = 10;

        if (requestData != null) {
            phone = requestData.getString("phone");
            date = requestData.getString("date").replace("-", "");
            starttime = requestData.getString("starttime").replace(":", "");
            endtime = requestData.getString("endtime").replace(":", "");
            startindex = requestData.getIntValue("startindex");
            pagesize = requestData.getIntValue("pagesize");
        }

        // 查询参数
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("phone", phone);
        paramMap.put("date", date);
        paramMap.put("starttime", starttime);
        paramMap.put("endtime", endtime);
        paramMap.put("startindex", startindex);
        paramMap.put("pagesize", pagesize);

        // 查询用户条数
        int orderListCnt = this.queryRepository.getOrderListCnt(paramMap);
        log.info("共有[{}]条数据.", orderListCnt);

        // 查询用户列表
        List<Map<String, Object>> orderList = this.queryRepository.getOrderList(paramMap);
        log.info("取得的预约信息为:{}", orderList);

        JSONObject result = new JSONObject();
        result.put("retcode", "0000");
        result.put("retmsg", "成功");
        result.put("cnt", orderListCnt);
        result.put("orderinfo", orderList);

        return result.toJSONString();
    }
}
