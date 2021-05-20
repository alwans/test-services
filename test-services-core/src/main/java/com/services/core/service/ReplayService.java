package com.services.core.service;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.domain.ReplayBo;
import com.services.core.domain.ReplayConfigBO;
import com.services.core.entity.Record;
import com.services.core.entity.Replay;
import com.baomidou.mybatisplus.extension.service.IService;
import com.services.core.http.WebResponseBaseResult;
import com.services.core.params.BaseParams;
import com.services.core.params.BatchReplayConfigParams;
import com.test.util.http.ResponseBaseResult;

import java.util.List;

/**
 *
 */
public interface ReplayService extends IService<Replay> {

    /**
     *
     * @param replayConfigBO
     * @return
     */
    ResponseBaseResult replay(ReplayConfigBO replayConfigBO);

    ResponseBaseResult getReplayBaseConfig(int recordId);

    RepeaterResult<String> saveRepeat(String body);

    Replay findByRepeatId(String repeatId);

    WebResponseBaseResult pageByParams(String appName, String repeatId, Integer pageIndex, Integer pageSize);

    ResponseBaseResult getDetail(int id);

    ResponseBaseResult getAppNameList();

    ResponseBaseResult getSandboxList(BaseParams baseParams);

    ResponseBaseResult batchReplay(BatchReplayConfigParams params);

    ResponseBaseResult getAllBatchHistory();

    List<Replay> getByMultiParams(Replay replay);
}
