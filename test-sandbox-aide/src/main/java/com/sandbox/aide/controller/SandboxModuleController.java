package com.sandbox.aide.controller;

import com.alibaba.fastjson.JSONObject;
import com.sandbox.aide.ModuleMgr;
import com.sandbox.aide.SandboxMgr;
import com.test.bean.SandboxObject;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wl on 2021/4/22.
 */
@RestController
@RequestMapping("/api/module")
public class SandboxModuleController {

    private static Logger log = LoggerFactory.getLogger(SandboxModuleController.class);

    @Autowired
    private ModuleMgr moduleMgr;


    @RequestMapping(value = "/getModuleList", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseBaseResult getAllModuleList(@RequestBody SandboxObject sandboxObject){
        if(sandboxObject.getServerName()==null){
            return ResponseBaseResult.failedResponseBaseResult().setMessage("serverName 参数不能为空");
        }
        if(null == SandboxMgr.loadedServerList.get(sandboxObject.getServerName())){
            return ResponseBaseResult.failedResponseBaseResult().setMessage(sandboxObject.getServerName()
                    +"中的sandbox服务未启动,请先启动sandbox服务");
        }
        List<Map> list = moduleMgr.getModuleList(SandboxMgr.loadedServerList.get(sandboxObject.getServerName()));
        return ResponseBaseResult.successResponseBaseResult().setData(list);
    }

    @RequestMapping(value = "/change/active/state", method = RequestMethod.POST, produces = "application/json;chaset=UTF-8")
    public ResponseBaseResult changeActiveState(@RequestBody JSONObject jsonObject){
        if((boolean)jsonObject.get("state")){
            boolean state = moduleMgr.avtiveModule(jsonObject.getObject("sandboxInfo", SandboxObject.class), (String)jsonObject.get("moduleId"));
            if(state) return ResponseBaseResult.successResponseBaseResult.setMessage("successfully");
            return ResponseBaseResult.failedResponseBaseResult.setMessage("激活失败");
        }
        boolean state = moduleMgr.forzenModule(jsonObject.getObject("sandboxInfo", SandboxObject.class), (String)jsonObject.get("moduleId"));
        if(state) return ResponseBaseResult.successResponseBaseResult.setMessage("successfully");
        return  ResponseBaseResult.failedResponseBaseResult.setMessage("冻结失败");
    }
}
