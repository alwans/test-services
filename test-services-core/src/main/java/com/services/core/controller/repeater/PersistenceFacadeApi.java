package com.services.core.controller.repeater;


import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.services.core.entity.Record;
import com.services.core.service.RecordService;
import com.services.core.service.ReplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @link repeater-console 移植过来的接口(和repeater module交互)
 */

@RestController
@RequestMapping("/facade/api")
public class PersistenceFacadeApi {
    private static final Logger log = LoggerFactory.getLogger(PersistenceFacadeApi.class);

    @Resource
    private RecordService recordService;

    @Resource
    private ReplayService replayService;

    /**
     * record保存
     * @param body
     * @return
     */
    @RequestMapping(value = "record/save", method = RequestMethod.POST)
    public RepeaterResult<String> recordSave(@RequestBody String body) {
        return recordService.saveRecord(body);
    }

    /**
     * 获取单条record
     * @param appName 这是真的appName，不是serverPid
     * @param traceId
     * @return
     */
    @RequestMapping(value = "record/{appName}/{traceId}", method = RequestMethod.GET)
    public RepeaterResult<String> getWrapperRecord(@PathVariable("appName") String appName,
                                                   @PathVariable("traceId") String traceId) {
//        Record record =recordService.selectByServerPidAndTraceId(Integer.parseInt(appName), traceId);
        Record record = recordService.selectByAppNameAndTraceId(appName, traceId);
        if (record == null) {
            return RepeaterResult.builder().success(false).message("data not exits").build();
        }
        return RepeaterResult.builder().success(true).message("operate success").data(record.getWrapperRecord()).build();
    }

    /**
     * 回放结果更新
     * @param body
     * @return
     */
    @RequestMapping(value = "repeat/save", method = RequestMethod.POST)
    public RepeaterResult<String> repeatSave(@RequestBody String body) {
        return replayService.saveRepeat(body);
    }

}
