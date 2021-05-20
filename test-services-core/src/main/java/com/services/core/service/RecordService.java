package com.services.core.service;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.domain.RecordDetailBO;
import com.services.core.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.services.core.entity.SandboxInfo;
import com.services.core.params.BatchReplayConfigParams;

import java.util.List;

/**
 *
 */
public interface RecordService extends IService<Record> {

    /**
     * 存储record
     *
     * @param body post内存
     * @return 存储结果
     */
    RepeaterResult<String> saveRecord(String body);

    /**
     * 分页查询
     * @param appName
     * @param traceId
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Record> pageByParams(String appName, String traceId, Integer currentPage, Integer pageSize);

    /**
     * 更新id，获取record详情
     * @param id
     * @return
     */
    RecordDetailBO getDetail(int id);

    Record selectByServerPidAndTraceId(int serverPid, String traceId);

    /**
     * 通过appName和traceId查询record记录
     * @param appName
     * @param traceId
     * @return
     */
    Record selectByAppNameAndTraceId(String appName, String traceId);

    /**
     * 根据批量回放配置信息，查询有效的record集合
     * @param batchReplayConfigParams
     * @return
     */
    List<Record> selectByBatchConfigParams(BatchReplayConfigParams batchReplayConfigParams);


}
