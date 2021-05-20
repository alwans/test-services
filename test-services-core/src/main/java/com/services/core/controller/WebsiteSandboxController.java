package com.services.core.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.SandboxInfo;
import com.services.core.http.AideHandle;
import com.services.core.http.WebResponseBaseResult;
import com.services.core.service.RepeaterConfigService;
import com.services.core.service.impl.SandboxInfoServiceImpl;
import com.test.util.http.HttpResult;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test.util.JsonUtils.objectConverion;

/**
 * web页面 操作sandbox的相关接口
 * Created by wl on 2021/4/25.
 */
@RestController
@RequestMapping("/api/website")
public class WebsiteSandboxController {

    private static Logger log = LoggerFactory.getLogger(WebsiteSandboxController.class);

    @Resource
    private AideHandle aideHandle;

    @Resource
    private SandboxInfoServiceImpl sandboxInfoService;

    @Resource
    private RepeaterConfigService repeaterConfigService;

    /**
     * 页面启动sandbox入口
     * @param id sandbox_info表中的id
     * @return
     */
    @RequestMapping(value = "/attachServer", method = RequestMethod.GET)
    public ResponseBaseResult attachServer(@RequestParam int id){
        if(id==0){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("id不允许为空");
        }
        SandboxInfo sandboxInfo = sandboxInfoService.selectById(id);
        if(sandboxInfo==null) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不正确");
        if(sandboxInfo.getAideServerPort()==null) return ResponseBaseResult.failedResponseBaseResult.setMessage("sandbox aide服务未启动");
        HttpResult httpResult = aideHandle.attachServer(sandboxInfo);
        if(httpResult.getCode()!=200 || httpResult.getBody4BaseResult().getCode()!=1){ //code!=200说明和aide服务通信失败
            return ResponseBaseResult.failedResponseBaseResult.setMessage("启动失败; "+ (httpResult.getBody4BaseResult() == null?
                    "sandbox aide服务未启动": httpResult.getBody4BaseResult().getMessage()));
        }
        SandboxInfo new_sandboxInfo = (SandboxInfo) objectConverion(httpResult.getBody4BaseResult().getData(), SandboxInfo.class);
        //更新数据库
        new_sandboxInfo.setSandboxStatus(true);
        boolean state = sandboxInfoService.saveOrUpdate(new_sandboxInfo);
        if(!state){
            log.error("数据更新失败");
        }
        return  ResponseBaseResult.successResponseBaseResult().setMessage("启动成功");
    }

    /**
     * 页面关闭sandbox服务的入口
     * @param id sandbox_info表中的id
     * @return
     */
    @RequestMapping(value = "/shutdown/sandbox", method = RequestMethod.GET)
    public ResponseBaseResult closeSandbox(@RequestParam int id){
        if(id==0)return ResponseBaseResult.failedResponseBaseResult.setMessage("id不允许为空");
        SandboxInfo sandboxInfo = sandboxInfoService.selectById(id);
        if(sandboxInfo==null) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不正确");
        if(sandboxInfo.getServerPid()==null) return ResponseBaseResult.failedResponseBaseResult.setMessage("sandbox aide服务未启动");
        HttpResult httpResult = aideHandle.shutdownSandbox(sandboxInfo);
        if(httpResult.getCode() != 200 || httpResult.getBody4BaseResult().getCode()!=1){
            //这种情况下，就是aide服务被关闭了,但是又不能确定应用服务的sandbox是否还是开启状态，
            //就不能直接去更新sandbox状态。暂时懒得去解决这个
            return ResponseBaseResult.failedResponseBaseResult.setMessage("sandbox关闭失败");
        }
        SandboxInfo new_sandboxInfo = (SandboxInfo) objectConverion(httpResult.getBody4BaseResult().getData(), SandboxInfo.class);
        new_sandboxInfo.setSandboxStatus(false);
        boolean state = sandboxInfoService.saveOrUpdate(new_sandboxInfo);
        if(!state){
            log.error("数据更新失败");
        }
        return  ResponseBaseResult.successResponseBaseResult().setMessage("sandbox关闭成功");
    }

    /**
     * 获取对应sandbox中的所有module模块信息
     * @param id sandbox_info表中的id
     * @return
     */
    @RequestMapping(value = "/module/list", method = RequestMethod.GET)
    public ResponseBaseResult getAllModuleList(@RequestParam int id){
        if(id==0){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("id不允许为空");
        }
        SandboxInfo sandboxInfo = sandboxInfoService.selectById(id);
        if(sandboxInfo==null) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不正确");
        if(sandboxInfo.getSandboxStatus()==null || !sandboxInfo.getSandboxStatus()) return ResponseBaseResult.failedResponseBaseResult.setMessage("sandbox 服务未启动");
        HttpResult httpResult = aideHandle.getAllModule(sandboxInfo);
        if(httpResult.getCode()!=200 ||  httpResult.getBody4BaseResult() == null || httpResult.getBody4BaseResult().getCode()!=1){
            String msg = "";
            if(httpResult.getBody4BaseResult() != null){ //如果连接aide服务成功，但是获取module list失败，那就修改sandbox状态为关闭
                sandboxInfo.setSandboxStatus(false);
                sandboxInfoService.saveOrUpdate(sandboxInfo);
            }else{
               msg =  httpResult.getBody4BaseResult() == null ?  "aide服务已关闭": httpResult.getBody4BaseResult().getMessage();
            }
            //失败情况下，把状态设置为false
            sandboxInfo.setSandboxStatus(false);
            sandboxInfoService.saveOrUpdate(sandboxInfo);
            return ResponseBaseResult.failedResponseBaseResult.setMessage("获取module列表失败; "+msg );
        }
        return ResponseBaseResult.successResponseBaseResult().setData(httpResult.getBody4BaseResult().getData());

    }

    /**
     * 获取所有的sandbox信息
     * @param currentPage
     * @param pageSize
     * @param env  sandboxInfo.env
     * @param host sandboxInfo.host
     * @param serverName sandboxInfo.serverName
     * @return
     */
    @RequestMapping(value = "/server/list", method = RequestMethod.GET)
    public ResponseBaseResult getServerList(@RequestParam int currentPage,
                                            @RequestParam int pageSize,
                                            @RequestParam  String env,
                                            @RequestParam String host,
                                            @RequestParam String serverName){
        pageSize = pageSize!=0? pageSize: 10;
        currentPage = currentPage !=0? currentPage: 1;
        Page<SandboxInfo> pages = sandboxInfoService.pageByParams(env, host, serverName, currentPage, pageSize);
        WebResponseBaseResult result = new WebResponseBaseResult();
        result.setData(pages.getRecords());
        Map<String, Long> page = new HashMap<>();
        page.put("total", pages.getTotal());
        result.setCode(ResponseBaseResult.StausCode.SUCCESS.getCode());
        result.setPage(page);
        result.setMessage("successfully");
        return result;
    }

    /**
     * 更新模块状态 ： 关闭 | 激活
     * @param id sandbox id
     * @param moduleId 模块id
     * @param state 更新后的状态
     * @return
     */
    @RequestMapping(value = "/change/active/state", method = RequestMethod.GET)
    public ResponseBaseResult changeActiveState(@RequestParam int id,
                                                @RequestParam String moduleId,
                                                @RequestParam boolean state){
        if(id ==0) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不能为空");
        if(null == moduleId || moduleId.equals("")) return ResponseBaseResult.failedResponseBaseResult.setMessage("moduleId不能为空");
        SandboxInfo sandboxInfo = sandboxInfoService.selectById(id);
        if(sandboxInfo == null) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不存在");
        HttpResult httpResult = aideHandle.changeModuleSate(sandboxInfo, moduleId, state);
        if(httpResult.getCode() != 200  || httpResult.getBody4BaseResult().getCode() !=1){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("模块操作失败");
        }
        return ResponseBaseResult.successResponseBaseResult.setMessage("successfully");
    }

    /**
     * 更新模块的记载状态 ： 加载|卸载 （不记得aide服务有没有实现对应的功能）
     * @param id sandbox id
     * @param moduleId 模块id
     * @param state 更新后的状态
     * @return
     */
    @RequestMapping(value = "/change/loaded/state", method = RequestMethod.GET)
    public ResponseBaseResult changeLoadedState(@RequestParam int id,
                                                @RequestParam String moduleId,
                                                @RequestParam boolean state){
        if(id ==0) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不能为空");
        if(null == moduleId || moduleId.equals("")) return ResponseBaseResult.failedResponseBaseResult.setMessage("moduleId不能为空");
        return ResponseBaseResult.successResponseBaseResult.setMessage("successfully");
    }

    /**
     * 规则：不管是新增还是更新，相同host和serverName，在数据库中有且仅有一条数据
     * @param sandboxInfo
     * @return
     */
    @RequestMapping(value = "/sandbox/insertOrUpdate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBaseResult SnadboxInsertOrUpadate(@RequestBody SandboxInfo sandboxInfo){
        SandboxInfo queryParam = new SandboxInfo();
        queryParam.setServerName(sandboxInfo.getServerName());
        queryParam.setHost(sandboxInfo.getHost());
        List<SandboxInfo> result = sandboxInfoService.getByMultiParams(queryParam);
        SandboxInfo old = result.size()>0? result.get(0) : null ;  //这里取查询结果的第一条
        if(sandboxInfo.getId() == null || sandboxInfo.getId() ==0 ){  //这里是新增数据的校验
            if(null != old) return ResponseBaseResult.failedResponseBaseResult.setMessage(String.format("host: %s, " +
                    "serverName: %s 数据已经存在", sandboxInfo.getHost(), sandboxInfo.getServerName()));
        }else{  //这里是更新的校验
            if(old!= null && old.getId() != sandboxInfo.getId())
                return ResponseBaseResult.failedResponseBaseResult.setMessage(String.format("host: %s, " +
                    "serverName: %s 数据已经存在", sandboxInfo.getHost(), sandboxInfo.getServerName()));
        }
        boolean state = sandboxInfoService.saveOrUpdate(sandboxInfo);
        if(!state){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("数据新增/更新失败");
        }
        return ResponseBaseResult.successResponseBaseResult.setMessage("数据新增/更新成功");
    }






}
