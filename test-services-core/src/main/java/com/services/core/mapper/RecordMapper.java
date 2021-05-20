package com.services.core.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.services.core.entity.SandboxInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.services.core.entity.Record
 */
public interface RecordMapper extends BaseMapper<Record> {

    Page<Record> pageByParams(Page<Record> page,
                                   @Param(value = "appName") String appName,
                                   @Param(value = "traceId") String traceId);

    Record selectByTraceIdAndServerPid(int serverPid, String traceId);
}




