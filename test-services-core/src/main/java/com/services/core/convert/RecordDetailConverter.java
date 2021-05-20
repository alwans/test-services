package com.services.core.convert;

import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.Serializer;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializerProvider;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.RecordWrapper;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Invocation;
import com.services.core.domain.InvocationBO;
import com.services.core.domain.RecordDetailBO;
import com.services.core.entity.Record;
import com.services.core.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@link }
 * <p>
 *
 * @author zhaoyb1990
 */
@Component("recordDetailConverter")
@Slf4j
public class RecordDetailConverter implements ModelConverter<Record, RecordDetailBO> {

    @Resource
    private ModelConverter<Invocation, InvocationBO> invocationConverter;

    @Override
    public RecordDetailBO convert(Record source) {
        RecordDetailBO rdb = new RecordDetailBO();
        // lazy mode , this isn't a correct way to copy properties.
        BeanUtils.copyProperties(source, rdb);
        Serializer hessian = SerializerProvider.instance().provide(Serializer.Type.HESSIAN);
        try {
            RecordWrapper wrapper = hessian.deserialize(source.getWrapperRecord(), RecordWrapper.class);
            rdb.setSubInvocations(
                    JacksonUtil.serialize(
                            Optional.ofNullable(wrapper.getSubInvocations())
                                    .orElse(Collections.emptyList())
                                    .stream().map(invocationConverter::convert)
                                    .collect(Collectors.toList())
                    )
            );
        } catch (SerializeException e) {
            log.error("error deserialize record wrapper", e);
        }
        return rdb;
    }

    @Override
    public Record reconvert(RecordDetailBO target) {
        return null;
    }
}
