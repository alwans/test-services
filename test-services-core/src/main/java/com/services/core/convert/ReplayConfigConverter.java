package com.services.core.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.Serializer;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializerProvider;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.RecordWrapper;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Invocation;
import com.services.core.domain.InvocationBO;
import com.services.core.domain.ReplayConfigBO;
import com.services.core.entity.Record;
import com.services.core.entity.Replay;
import com.services.core.entity.SandboxInfo;
import com.services.core.service.SandboxInfoService;
import com.services.core.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component("replayConfigConverter")
@Slf4j
public class ReplayConfigConverter implements ModelConverter<Record, ReplayConfigBO> {

    @Resource
    private SandboxInfoService sandboxInfoService;

    @Resource
    private ModelConverter<Invocation, InvocationBO> invocationConverter;

    @Resource
    private SubInvokeListConverter subInvokeListConverter;

    @Override
    public ReplayConfigBO convert(Record source) {
        ReplayConfigBO replayConfigBO = new ReplayConfigBO();
        replayConfigBO.setUseRecordHost(true);
        replayConfigBO.setEnableMock(false);
        replayConfigBO.setEnableAllMock(true);
        replayConfigBO.setNeedMockSubInvokeList(Collections.emptyList());
        replayConfigBO.setOrigin_sandbox_id(source.getSandboxId());
        replayConfigBO.setRecord_id(source.getId());
        replayConfigBO.setSelectedHost(source.getSandboxId());
        replayConfigBO.setHostList(sandboxInfoService.selectByServerName(source.getAppName()));
        //set  subInvokeList
        Serializer hessian = SerializerProvider.instance().provide(Serializer.Type.HESSIAN);
        try {
            RecordWrapper wrapper = hessian.deserialize(source.getWrapperRecord(), RecordWrapper.class);
            List<InvocationBO> list = Optional.ofNullable(wrapper.getSubInvocations())
                    .orElse(Collections.emptyList())
                    .stream().map(invocationConverter::convert)
                    .collect(Collectors.toList());
            replayConfigBO.setSubInvokeList(subInvokeListConverter.convert(list));
        } catch (SerializeException e) {
            log.error("error deserialize record wrapper", e);
        }
        return replayConfigBO;
    }

    @Override
    public Record reconvert(ReplayConfigBO target) {
        return null;
    }
}
