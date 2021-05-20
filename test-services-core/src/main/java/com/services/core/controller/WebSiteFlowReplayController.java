package com.services.core.controller;

import com.services.core.domain.BatchHistoryBO;
import com.services.core.domain.ReplayConfigBO;
import com.services.core.entity.Record;
import com.services.core.http.WebResponseBaseResult;
import com.services.core.params.BaseParams;
import com.services.core.params.BatchReplayConfigParams;
import com.services.core.service.ReplayService;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * web页面 回放相关接口
 */
@RestController
@RequestMapping("api/website/flow/replay")
public class WebSiteFlowReplayController {

    private static final Logger log = LoggerFactory.getLogger(WebSiteFlowReplayController.class);

    @Resource
    private ReplayService replayService;

    /**
     * 根据record_id，获取相关的基础回放配置信息
     * @param id
     * @return
     */
    @RequestMapping(value = "config",method = RequestMethod.GET)
    public ResponseBaseResult replayConfig(@RequestParam int id){
        if(id ==0) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不能为空");
        return replayService.getReplayBaseConfig(id);
    }

    /**
     * 执行单条流量回放
     * @param replayConfigBO
     * @return
     */
    @RequestMapping(value = "single/replay", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseBaseResult singleReplay(@RequestBody ReplayConfigBO replayConfigBO){
        if(replayConfigBO ==null) return ResponseBaseResult.failedResponseBaseResult.setMessage("参数不正确");
        return replayService.replay(replayConfigBO);
    }

    /**
     * 回放列表
     * @param appName  应用名
     * @param repeatId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public WebResponseBaseResult replayList(@RequestParam String appName,
                                            @RequestParam String repeatId,
                                            @RequestParam Integer currentPage,
                                            @RequestParam Integer pageSize
                                         ){
        currentPage = currentPage != null && currentPage !=0? currentPage: 1;
        pageSize = pageSize != null && pageSize !=0? pageSize: 10;
        return replayService.pageByParams(appName, repeatId, currentPage, pageSize);
    }

    /**
     * 回放详情
     * @param id
     * @return
     */
    @RequestMapping(value = "detail",method = RequestMethod.GET)
    public ResponseBaseResult replayDetail(@RequestParam int id){
        if(id ==0) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不能为空");
        return replayService.getDetail(id);
    }

    /**
     * 返回所有的appName列表（用于批量回放配置页面）
     * @return
     */
    @RequestMapping(value = "appName/list",method = RequestMethod.GET)
    public ResponseBaseResult appNameList(){
        return replayService.getAppNameList();
    }

    /**
     * 返回对用应用的所有机器 （用于批量回放配置页面）
     * @param params
     * @return
     */
    @RequestMapping(value = "sandbox/list",method = RequestMethod.GET)
    public ResponseBaseResult sandboxList(@ModelAttribute("BaseParams")BaseParams params){
        if(params.getAppName() == null) return ResponseBaseResult.failedResponseBaseResult.setMessage("appName 不能为空");
        return replayService.getSandboxList(params);
    }

    /**
     * 执行批量回放
     * @param params
     * @return
     */
    @RequestMapping(value = "exec/batch/replay",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBaseResult batchReplay(@RequestBody BatchReplayConfigParams params){
        if(params.getRecordList().size()>0){
            for(Record record: params.getRecordList()){
                if(!record.getAppName().equals(params.getAppName())){
                    return ResponseBaseResult.failedResponseBaseResult.setMessage("勾选记录和配置中的appName不一致");
                }
            }
        }
        return replayService.batchReplay(params);
    }

    /**
     * 批量回放历史记录列表
     * @return
     */
    @RequestMapping(value = "batch/history/list",method = RequestMethod.GET)
    public ResponseBaseResult getBatchHistoryList(){
        ResponseBaseResult result = replayService.getAllBatchHistory();
        List<BatchHistoryBO> list = (List<BatchHistoryBO>)result.getData();
        WebResponseBaseResult webResponseBaseResult = new WebResponseBaseResult();
        webResponseBaseResult.setData(list);
        Map<String, Integer> page = new HashMap<>();
        page.put("total", list.size() );
        webResponseBaseResult.setCode(ResponseBaseResult.StausCode.SUCCESS.getCode());
        webResponseBaseResult.setPage(page);
        webResponseBaseResult.setMessage("successfully");
        return webResponseBaseResult;
    }
}
