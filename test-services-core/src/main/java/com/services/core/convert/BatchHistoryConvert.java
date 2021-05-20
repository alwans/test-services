package com.services.core.convert;

import com.baomidou.mybatisplus.extension.api.R;
import com.services.core.domain.BatchHistoryBO;
import com.services.core.entity.Replay;
import com.services.core.service.ReplayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("BatchHistoryConvert")
@Slf4j
public class BatchHistoryConvert implements ModelConverter<Replay, BatchHistoryBO>{

    @Resource
    private ReplayService replayService;

    @Override
    public BatchHistoryBO convert(Replay source) {
        List<Replay> list = replayService.getByMultiParams(source);
        int successTotal = 0;
        for(Replay replay: list){
            if(replay.getSuccess())  successTotal++;
        }
        BatchHistoryBO batchHistoryBO = new BatchHistoryBO();
        if(list.size()>0){
            batchHistoryBO.setTotal(list.size());
            batchHistoryBO.setAppName(list.get(0).getAppName());
            batchHistoryBO.setEnvironment(list.get(0).getEnvironment());
            batchHistoryBO.setHost(list.get(0).getIp());
            batchHistoryBO.setSuccessTotal(successTotal);
            batchHistoryBO.setFailedTotal(list.size()-successTotal);
            batchHistoryBO.setGmtCreate(source.getGmtCreate());
        }
        return batchHistoryBO;
    }

    @Override
    public Replay reconvert(BatchHistoryBO target) {
        return null;
    }
}
