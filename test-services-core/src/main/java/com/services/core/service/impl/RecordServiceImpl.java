package com.services.core.service.impl;

import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.RecordWrapper;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.SerializerWrapper;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.services.core.convert.ModelConverter;
import com.services.core.domain.RecordDetailBO;
import com.services.core.entity.Record;
import com.services.core.entity.SandboxInfo;
import com.services.core.params.BatchReplayConfigParams;
import com.services.core.service.RecordService;
import com.services.core.mapper.RecordMapper;
import com.services.core.service.SandboxInfoService;
import com.services.core.util.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
implements RecordService{

    @Resource
    private RecordMapper recordMapper;

    @Resource
    private ModelConverter<Record, RecordDetailBO> recordDetailConverter;

    @Resource
    private SandboxInfoService sandboxInfoService;

    @Override
    public RepeaterResult<String> saveRecord(String body) {
        try{
            RecordWrapper wrapper = SerializerWrapper.hessianDeserialize(body, RecordWrapper.class);
            if (wrapper == null || StringUtils.isEmpty(wrapper.getAppName())) {
                return RepeaterResult.builder().success(false).message("invalid request").build();
            }
            Record record = ConvertUtil.convertWrapper(wrapper, body);
            //query sandbox_info
            SandboxInfo sandbox_query = new SandboxInfo();
            sandbox_query.setServerPid(Integer.parseInt(record.getAppName()));
            sandbox_query.setSandboxPort(Integer.parseInt(record.getEnvironment()));
            List<SandboxInfo> list = sandboxInfoService.getByMultiParams(sandbox_query);
            if(list.size()!=1){
                log.error("sandbox object not exist | Multiple sandbox object, size: "+list.size()+" counts");
                return RepeaterResult.builder().success(false).message("sandbox object not exist | Multiple sandbox object").build();
            }
            SandboxInfo sandboxInfo = list.get(0);
            record.setSandboxId(sandboxInfo.getId().intValue());
            record.setAppName(sandboxInfo.getServerName());
            record.setEnvironment(sandboxInfo.getEnv());
            recordMapper.insert(record);
            return RepeaterResult.builder().success(true).message("operate success").data("-/-").build();
        }catch (Throwable throwable){
            return RepeaterResult.builder().success(false).message(throwable.getMessage()).build();
        }
    }

    @Override
    public Page<Record> pageByParams(String appName, String traceId, Integer currentPage, Integer pageSize) {
        return recordMapper.pageByParams(new Page<>(currentPage, pageSize), appName, traceId);
    }

    @Override
    public RecordDetailBO getDetail(int id) {
        Record record = recordMapper.selectById(id);
        return recordDetailConverter.convert(record);
    }

    @Override
    public Record selectByServerPidAndTraceId(int serverPid, String traceId) {
        return recordMapper.selectByTraceIdAndServerPid(serverPid, traceId);
    }

    @Override
    public Record selectByAppNameAndTraceId(String appName, String traceId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("app_name", appName);
        qw.eq("trace_id", traceId);
        return recordMapper.selectOne(qw);
    }

    @Override
    public List<Record> selectByBatchConfigParams(BatchReplayConfigParams batchReplayConfigParams) {
        QueryWrapper<Record> qw = new QueryWrapper();
        if(batchReplayConfigParams.getDate() != null && batchReplayConfigParams.getDate().size()>0){
            Date startTime = batchReplayConfigParams.getDate().get(0);
            Date endTime =  batchReplayConfigParams.getDate().get(1);
            qw.ge("gmt_create", startTime);
            qw.le("gmt_create", endTime);
            qw.orderByDesc("gmt_create");
        }
        if(batchReplayConfigParams.getAppName()!= null){
            qw.eq("app_name", batchReplayConfigParams.getAppName());
        }
        return recordMapper.selectList(qw);
    }
}




