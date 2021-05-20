package com.services.core.convert;

import com.services.core.domain.ReplayBo;
import com.services.core.entity.Record;
import com.services.core.entity.Replay;
import com.services.core.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("ReplayBOConverter")
@Slf4j
public class ReplayBOConverter implements ModelConverter<Replay, ReplayBo>{

    @Resource
    private RecordService recordService;

    @Override
    public ReplayBo convert(Replay source) {
        ReplayBo replayBo = new ReplayBo();
        BeanUtils.copyProperties(source, replayBo);
        Record record = recordService.getById(source.getRecordId());
        replayBo.setEntranceDesc(record.getEntranceDesc());
        return  replayBo;
    }

    @Override
    public Replay reconvert(ReplayBo target) {
        Replay replay = new Replay();
        BeanUtils.copyProperties(target, replay);
        return replay;
    }
}
