package com.sandbox.aide.controller;

import com.alibaba.fastjson.JSONObject;
import com.sandbox.aide.SandboxMgr;
import com.sandbox.aide.module.RepeaterModuleMgr;
import com.sandbox.aide.util.Constants;
import com.test.bean.SandboxObject;
import com.test.util.http.HttpResult;
import com.test.util.http.ResponseBaseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/module/repeat")
public class RepeatModuleController {

    @Resource
    private SandboxMgr sandboxMgr;

    @Resource
    private RepeaterModuleMgr repeaterModuleMgr;

    @RequestMapping(value = "exec/single/replay",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseBaseResult execSingleReplay(@RequestBody JSONObject requestParams){
        String data = (String)requestParams.get(Constants.DATA_TRANSPORT_IDENTIFY);
        String serverName = (String) requestParams.get("serverName");
        if(data==null || serverName==null)
            return ResponseBaseResult.failedResponseBaseResult.setMessage("回放信息数据不能为空");
        SandboxObject sandboxObject = sandboxMgr.getLoadedSandboxInstance(serverName);
        if( sandboxObject == null)
            return ResponseBaseResult.failedResponseBaseResult.setMessage("对应服务sandbox未启动");
        HttpResult httpResult = repeaterModuleMgr.execSingleReplay(sandboxObject, data);
        if(httpResult== null || httpResult.getCode() != 200 ){
            return ResponseBaseResult.failedResponseBaseResult.setMessage(httpResult.getBody());
        }
        return ResponseBaseResult.successResponseBaseResult.setMessage(httpResult.getBody());
    }
}
