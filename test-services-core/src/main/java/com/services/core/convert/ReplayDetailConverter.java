package com.services.core.convert;

import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.MockInvocation;
import com.services.core.domain.DifferenceBO;
import com.services.core.domain.ReplayDetailBO;
import com.services.core.entity.Replay;
import com.services.core.service.RecordService;
import com.services.core.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("ReplayDetailConverter")
@Slf4j
public class ReplayDetailConverter implements ModelConverter<Replay, ReplayDetailBO>{

    @Resource
    private MockInvocationConvert mockInvocationConvert;

    @Resource
    private RecordDetailConverter recordDetailConverter;

    @Resource
    private RecordService recordService;

    @Override
    public ReplayDetailBO convert(Replay source) {
        ReplayDetailBO  replayDetailBO = new ReplayDetailBO();
        BeanUtils.copyProperties(source, replayDetailBO);
        try {
            List<MockInvocation> mockInvocations = JacksonUtil.deserializeArray(source.getMockInvocation(), MockInvocation.class);
            replayDetailBO.setMockInvocations(
                    Optional.ofNullable(mockInvocations)
                            .orElse(Collections.emptyList())
                            .stream().map(mockInvocationConvert::convert)
                            .collect(Collectors.toList())
            );
            replayDetailBO.setDifferences(JacksonUtil.deserializeArray(source.getDiffResult(), DifferenceBO.class));
        } catch (SerializeException e) {
            //
        }
        replayDetailBO.setRecord(recordDetailConverter.convert(recordService.getById(source.getRecordId())));
        return replayDetailBO;
    }

    @Override
    public Replay reconvert(ReplayDetailBO target) {
        Replay replay = new Replay();
        BeanUtils.copyProperties(target, replay);
        return replay;
    }
}
