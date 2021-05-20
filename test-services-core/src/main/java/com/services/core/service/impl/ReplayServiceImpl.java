package com.services.core.service.impl;

import com.alibaba.jvm.sandbox.repeater.aide.compare.Comparable;
import com.alibaba.jvm.sandbox.repeater.aide.compare.ComparableFactory;
import com.alibaba.jvm.sandbox.repeater.aide.compare.CompareResult;
import com.alibaba.jvm.sandbox.repeater.plugin.Constants;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.trace.TraceGenerator;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.SerializerWrapper;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeatMeta;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeatModel;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.jvm.sandbox.repeater.plugin.spi.MockStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.services.core.convert.*;
import com.services.core.domain.*;
import com.services.core.entity.Record;
import com.services.core.entity.Replay;
import com.services.core.entity.SandboxInfo;
import com.services.core.http.AideHandle;
import com.services.core.http.WebResponseBaseResult;
import com.services.core.params.BaseParams;
import com.services.core.params.BatchReplayConfigParams;
import com.services.core.service.RecordService;
import com.services.core.service.ReplayService;
import com.services.core.mapper.ReplayMapper;
import com.services.core.service.SandboxInfoService;
import com.services.core.util.ConvertUtil;
import com.services.core.util.JacksonUtil;
import com.test.util.http.HttpResult;
import com.test.util.http.ResponseBaseResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.test.util.GaStringUtils.matching;

/**
 *
 */
@Service
public class ReplayServiceImpl extends ServiceImpl<ReplayMapper, Replay>
implements ReplayService{

    private static final Logger log = LoggerFactory.getLogger(ReplayServiceImpl.class);

    @Resource
    private RecordService recordService;

    @Resource
    private ReplayMapper replayMapper;

    @Resource
    private SandboxInfoService sandboxInfoService;

    @Resource
    private AideHandle aideHandle;

    @Resource
    private ReplayService replayService;

    @Resource
    private ModelConverter<Record, ReplayConfigBO> replayConfigBOModelConverter;

    @Resource
    private DifferenceConvert differenceConvert;

    @Resource
    private ReplayBOConverter replayBOConverter;

    @Resource
    private ReplayDetailConverter replayDetailConverter;

    @Resource
    private BatchHistoryConvert batchHistoryConvert;

    public ResponseBaseResult replay(ReplayConfigBO replayConfigBO){
        if(replayConfigBO.getRecord_id() ==0) return ResponseBaseResult.failedResponseBaseResult.setMessage("record id 为无效id");
        Record record = recordService.getById(replayConfigBO.getRecord_id());
        SandboxInfo sandboxInfo = findSelectedSandbox(replayConfigBO);
        if(!sandboxInfo.getSandboxStatus()){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("机器："+sandboxInfo.getHost() +"已离线，无法回放");
        }
        replayConfigBO.setAppName(sandboxInfo.getServerName());
        replayConfigBO.setSandboxInfo(sandboxInfo);
        int repeat_id = saveReplay(record, replayConfigBO);
        if(repeat_id==0){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("replay保存失败");
        }
        return doRepeat(record, replayConfigBO);
    }

    /**
     * 返回回放需要的配置信息
     * @param recordId
     * @return
     */
    @Override
    public ResponseBaseResult getReplayBaseConfig(int recordId) {
        Record record = recordService.getById(recordId);
        if(null == record) return ResponseBaseResult.failedResponseBaseResult.setMessage("record id不存在");
        ReplayConfigBO replayConfigBO = replayConfigBOModelConverter.convert(record);
        return ResponseBaseResult.successResponseBaseResult.setData(replayConfigBO).setMessage("successfully");
    }

    @Override
    public RepeaterResult<String> saveRepeat(String body) {
        RepeatModel rm;
        try {
            rm = SerializerWrapper.hessianDeserialize(body, RepeatModel.class);
        } catch (SerializeException e) {
            log.error("error occurred when deserialize repeat model", e);
            return RepeaterResult.builder().message("operate failed").build();
        }
        // this process must handle by async
        Replay replay = replayService.findByRepeatId(rm.getRepeatId());
        replay.setStatus(rm.isFinish() ? ReplayStatus.FINISH.getStatus() : ReplayStatus.FAILED.getStatus());
        replay.setTraceId(rm.getTraceId());
        replay.setCost(rm.getCost());
        Object expect;
        Object actual;
        try {
            if (rm.getResponse() instanceof String) {
                replay.setResponse(ConvertUtil.convert2Json((String)rm.getResponse()));
                try {
                    actual = JacksonUtil.deserialize((String)rm.getResponse(), Object.class);
                } catch (SerializeException e) {
                    actual = rm.getResponse();
                }
            } else {
                replay.setResponse(JacksonUtil.serialize(rm.getResponse()));
                actual = rm.getResponse();
            }
            replay.setMockInvocation(JacksonUtil.serialize(rm.getMockInvocations()));
            Record record = recordService.getById(replay.getRecordId());
            try {
                expect = JacksonUtil.deserialize(record.getResponse(), Object.class);
            } catch (SerializeException e) {
                expect = record.getResponse();
            }
        } catch (SerializeException e) {
            log.error("error occurred serialize replay response", e);
            return RepeaterResult.builder().message("operate failed").build();
        }
        Comparable comparable = ComparableFactory.instance().createDefault();
        // simple compare
        CompareResult result = comparable.compare(actual, expect);
        replay.setSuccess(!result.hasDifference());
        try {
            replay.setDiffResult(JacksonUtil.serialize(result.getDifferences()
                    .stream()
                    .map(differenceConvert::convert)
                    .collect(Collectors.toList()), false));
        } catch (SerializeException e) {
            log.error("error occurred serialize diff result", e);
            return RepeaterResult.builder().message("operate failed").build();
        }
        replayService.saveOrUpdate(replay);
//        Replay calllback = replayDao.saveAndFlush(replay);
        return RepeaterResult.builder().success(true).message("operate success").data("-/-").build();
    }

    @Override
    public Replay findByRepeatId(String repeatId){
        QueryWrapper qw = new QueryWrapper();
        qw.eq("repeat_id", repeatId);
        return replayMapper.selectOne(qw);
    }

    @Override
    public WebResponseBaseResult pageByParams(String appName, String repeatId, Integer pageIndex, Integer pageSize) {
        Page<Replay> pages = replayMapper.pageByParams(new Page<>(pageIndex, pageSize), appName, repeatId);
        List<Replay> list = pages.getRecords();
        List<ReplayBo> new_list = new ArrayList<>(list.size());
        for(Replay replay: list){
            new_list.add(replayBOConverter.convert(replay));
        }
        WebResponseBaseResult result = new WebResponseBaseResult();
        result.setData(new_list);
        Map<String, Long> page = new HashMap<>();
        page.put("total", pages.getTotal());
        result.setCode(ResponseBaseResult.StausCode.SUCCESS.getCode());
        result.setPage(page);
        result.setMessage("successfully");
        return result;
    }

    @Override
    public ResponseBaseResult getDetail(int id) {
        Replay replay = replayMapper.selectById(id);
        ReplayDetailBO replayDetailBO = replayDetailConverter.convert(replay);
        Map<String, Object> data = new HashMap<>(2);
        data.put("replay", replayDetailBO);
        data.put("record", replayDetailBO.getRecord());
        return ResponseBaseResult.successResponseBaseResult().setData(data).setMessage("successfully");
    }

    @Override
    public ResponseBaseResult getAppNameList() {
        List<SandboxInfo> list = sandboxInfoService.groupByAppNameList();
        return ResponseBaseResult.successResponseBaseResult().setData(list).setMessage("successfully");
    }

    @Override
    public ResponseBaseResult getSandboxList(BaseParams baseParams) {
        List<SandboxInfo> list = sandboxInfoService.selectByServerName(baseParams.getAppName());
        return ResponseBaseResult.successResponseBaseResult().setData(list).setMessage("successfully");
    }

    @Override
    public ResponseBaseResult batchReplay(BatchReplayConfigParams params) {
        List<Record> list = recordService.selectByBatchConfigParams(params);
        Set<Record> recordSet = new HashSet<>(list);
        recordSet.addAll(params.getRecordList());//去重合并
        list = new ArrayList<>(recordSet);
        for(int i=0; i<list.size(); i++){ //去掉不匹配url的流量
            Record record = list.get(i);
            boolean flag = false;
            for(String rule: params.getHttpEntrancePatterns()){
                if(matching(record.getEntranceDesc(), rule)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                list.remove(i);
                i--;
            }
        }
        return batchSaveReplay(list, params);
    }

    @Override
    public ResponseBaseResult getAllBatchHistory() {
        List<Replay> batchDateList = getAllBatchDate();
        List<BatchHistoryBO> list = new ArrayList<>(batchDateList.size());
        for(Replay replay: batchDateList){
            list.add(batchHistoryConvert.convert(replay));
        }
        return ResponseBaseResult.successResponseBaseResult().setData(list).setMessage("successfully");
    }

    @Override
    public List<Replay> getByMultiParams(Replay replay) {
        QueryWrapper<Replay> qw = new QueryWrapper<>();
        Class clz = replay.getClass();
        Field[] fields = clz.getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            try {
                Object value = field.get(replay);
                if(value != null && !value.equals("")){
                    if(field.getName().equals("id")){
                        TableId tableId = field.getAnnotation(TableId.class);
                        qw = qw.eq(tableId.value(), value);
                        return replayMapper.selectList(qw);
                    }else if(!field.getName().equals("serialVersionUID")){ //过滤掉
                        TableField tableField = field.getAnnotation(TableField.class);
                        qw = qw.eq(tableField.value(), value);
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("反射获取查询参数时异常", e);
            }
        }
        return replayMapper.selectList(qw);
    }

    private List<Replay> getAllBatchDate(){
        QueryWrapper<Replay> qw = new QueryWrapper<>();
        qw.select("gmt_create");//查询自定义列
        qw.eq("batch", 1);
        qw.groupBy("gmt_create");
        qw.orderByDesc("gmt_create");
        return replayMapper.selectList(qw);
    }

    private ResponseBaseResult batchSaveReplay(List<Record> list, BatchReplayConfigParams params){
        Date createDate = new Date();
        ReplayConfigBO replayConfigBO = new ReplayConfigBO();
        replayConfigBO.setEnableMock(params.getEnableMock());
        replayConfigBO.setSelectedHost(params.getSelectedHost());
        SandboxInfo sandboxInfo = findSelectedSandbox(replayConfigBO);
        replayConfigBO.setAppName(sandboxInfo.getServerName());
        replayConfigBO.setSandboxInfo(sandboxInfo);
        replayConfigBO.setBatch(true);
        for(Record record: list){
            replayConfigBO.setRepeat_id(null); //每次新增数据前，把上一次的repeat_id清除掉
            int state = saveReplay(record, replayConfigBO, createDate);
            if(state!=0){
                doRepeat(record, replayConfigBO);
            }else{
                log.error("批量执行中， record_id:{} 记录插入replay表失败, record_detail: {}", record.getId(), record);
            }
        }
        return ResponseBaseResult.successResponseBaseResult.setMessage("批量执行成功，执行流量总数："+ list.size() + "条");
    }

    private ResponseBaseResult doRepeat(Record record, ReplayConfigBO replayConfigBO){
        RepeatMeta repeatMeta = new RepeatMeta();
        repeatMeta.setAppName(record.getAppName());
        repeatMeta.setTraceId(record.getTraceId());
        repeatMeta.setMock(replayConfigBO.isEnableMock());
        repeatMeta.setRepeatId(replayConfigBO.getRepeat_id());
        repeatMeta.setStrategyType(MockStrategy.StrategyType.PARAMETER_MATCH);
        Map<String, String> requestParams = new HashMap<String, String>(2);
        SandboxInfo sandboxInfo = replayConfigBO.getSandboxInfo();
        try {
            requestParams.put(Constants.DATA_TRANSPORT_IDENTIFY, SerializerWrapper.hessianSerialize(repeatMeta));
            requestParams.put("serverName", sandboxInfo.getServerName());
        } catch (SerializeException e) {
            return ResponseBaseResult.failedResponseBaseResult.setMessage(e.getMessage());
        }
        HttpResult httpResult = aideHandle.execSingleReplay(sandboxInfo, requestParams);
        if(httpResult.getCode()==200 && httpResult.getBody4BaseResult() != null
                && httpResult.getBody4BaseResult().getCode()==1){
            return ResponseBaseResult.successResponseBaseResult.setMessage("successfully");
        }
        if(httpResult.getCode() != 200){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("回放失败, aide服务请求失败");
        }
        return ResponseBaseResult.failedResponseBaseResult().setMessage(httpResult.getBody4BaseResult().getMessage());

    }

    /**
     * 找出需要执行回放的机器
     * @param replayConfigBO
     * @return SandboxInfo
     */
    private SandboxInfo findSelectedSandbox(ReplayConfigBO replayConfigBO){
        SandboxInfo sandboxInfo;
        if(replayConfigBO.isUseRecordHost()){
            sandboxInfo = sandboxInfoService.selectById(replayConfigBO.getOrigin_sandbox_id());
        }else{
            sandboxInfo = sandboxInfoService.selectById(replayConfigBO.getSelectedHost());
        }
        return sandboxInfo;
    }

    private int saveReplay(Record record, ReplayConfigBO replayConfigBO){
       return saveReplay(record, replayConfigBO, new Date());
    }



    private int saveReplay(Record record, ReplayConfigBO replayConfigBO, Date createDate){
        Replay replay = new Replay();
        replay.setRecordId(record.getId());
//        replay.setAppName(record.getAppName());//这里是录制机器的appName
        replay.setAppName(replayConfigBO.getAppName());  //这里取的是回放机器的serverName
        replay.setEnvironment(record.getEnvironment());
//        replay.setIp(record.getHost()); //这里是每条流量的ip
        replay.setIp(replayConfigBO.getSandboxInfo().getHost());
        if (StringUtils.isEmpty(replayConfigBO.getRepeat_id())) {
            replayConfigBO.setRepeat_id(TraceGenerator.generate());
        }
        replay.setRepeatId(replayConfigBO.getRepeat_id()); //生成repeaterid
        replay.setGmtCreate(createDate);
        replay.setGmtModified(new Date());
        replay.setStatus(ReplayStatus.PROCESSING.getStatus());
        replay.setBatch(replayConfigBO.getBatch());
        // 冗余了一个repeatID，实际可以直接使用replay#id
        return replayMapper.insert(replay);
    }
}




