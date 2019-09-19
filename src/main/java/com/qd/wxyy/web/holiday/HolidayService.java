package com.qd.wxyy.web.holiday;

import com.alibaba.fastjson.JSONObject;
import com.qd.wxyy.core.SysErrorRsp;
import com.qd.wxyy.util.CommonUtil;
import com.qd.wxyy.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节假日管理Service.
 *
 * @author 张孝党 2019/07/16.
 * @version V0.0.1.
 * <p>
 * 更新履历： V0.0.1 2019/07/16 张孝党 创建.
 */
@Slf4j
@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    /**
     * 初始化节假日.
     */
    @Transactional(rollbackFor = Exception.class)
    public void initHoliday() {

        // 节假日列表
        List<String> holidays = new ArrayList<>();

        try {
            String year = DateTimeUtil.getCurrentYear();
            log.info("当年年份为：{}", year);

            String startDate = DateTimeUtil.getCurrentDate();
            String endDate = DateTimeUtil.getCurrentYear() + "1231";

            // 取出所当前日期开始到年底所有的日期
            List<String> dateList = DateTimeUtil.getDateList(startDate, endDate);
            for (String dd : dateList) {
                boolean isHoliday = DateTimeUtil.isWeekend(dd);
                log.info(">>>>>>>>>{}是否是周末{}", dd, isHoliday);
                if (isHoliday) {
                    holidays.add(dd);
                }
            }

            for (String holiday : holidays) {
                Map<String, String> param = new HashMap<>();
                param.put("id", CommonUtil.getUUid());
                param.put("year", year);
                param.put("rq", holiday);
                param.put("bz", "周末(自动生成)");

                this.holidayRepository.initData(param);
            }
        } catch (Exception ex) {
            log.error("节假日初始化时失败：" + ex.getMessage());
        }
    }

    /**
     * 查询数据.
     */
    public String queryHolidayList(JSONObject requestData) {

        String year = DateTimeUtil.getCurrentYear();
        String rq = "";
        int startindex = 0;
        int pagesize = 10;

        if (requestData != null) {
            rq = requestData.getString("rq");
            startindex = requestData.getIntValue("startindex");
            pagesize = requestData.getIntValue("pagesize");
        }

        // 查询参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("year", year);
        paramMap.put("rq", rq);
        paramMap.put("startindex", startindex);
        paramMap.put("pagesize", pagesize);

        // 查询条数
        int cnt = this.holidayRepository.queryCnt(paramMap);
        log.info("共有{}条数据", cnt);

        // 查询列表
        List<Map<String, String>> dataList = this.holidayRepository.queryList(paramMap);
        log.info("查询的结果为:", dataList);

        JSONObject result = new JSONObject();
        result.put("retcode", "0000");
        result.put("retmsg", "成功");
        result.put("year", year);
        result.put("cnt", cnt);
        result.put("holidays", dataList);

        return result.toJSONString();
    }

    /**
     * 新增.
     */
    @Transactional(rollbackFor = Exception.class)
    public String addData(JSONObject requestData) {

        // 本年度
        String year = DateTimeUtil.getCurrentYear();

        // 先判读是否存在
        Map<String, String> param1 = new HashMap<>();
        param1.put("year", year);
        param1.put("rq", requestData.getString("rq").replace("-", ""));
        if (this.holidayRepository.checkData(param1) != null) {
            return new SysErrorRsp("0004", "新增日期已经存在!").toJsonString();
        }

        // 再插入
        Map<String, String> param2 = new HashMap<>();
        param2.put("id", CommonUtil.getUUid());
        param2.put("year", year);
        param2.put("rq", requestData.getString("rq").replace("-", ""));
        param2.put("bz", requestData.getString("bz"));
        this.holidayRepository.addData(param2);

        return new SysErrorRsp("0000", "成功").toJsonString();
    }

    /**
     * 删除.
     */
    @Transactional(rollbackFor = Exception.class)
    public String delData(JSONObject requestData) {

        this.holidayRepository.delData(requestData.getString("id"));

        return new SysErrorRsp("0000", "成功").toJsonString();
    }

    /**
     * 指定日期是否是节假日.
     */
    public boolean isHoliday(String targetDate) {

        boolean holiday = false;

        int cnt = this.holidayRepository.isHoliday(targetDate);
        // 不是节假日
        if (cnt == 0) {
            holiday = false;
        } else {
            // 是节假日
            holiday = true;
        }

        return holiday;
    }
}
