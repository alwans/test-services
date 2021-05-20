package com.sandbox.aide.controller;

import com.alibaba.fastjson.JSONObject;
import com.sandbox.aide.SandboxMgr;
import com.sandbox.aide.module.RepeaterModuleMgr;
import com.sandbox.aide.util.Constants;
import com.test.bean.SandboxObject;
import com.test.util.http.HttpResult;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/module/config")
public class ModuleConfigController {

    private static final Logger log = LoggerFactory.getLogger(ModuleConfigController.class);

    @Resource
    private SandboxMgr sandboxMgr;

    @Resource
    private RepeaterModuleMgr repeaterModuleMgr;

    @RequestMapping(value = "/push/repeater/config", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBaseResult repeaterPushConfig(@RequestBody JSONObject params){
        String config = (String)params.get("config");
        String serverName = (String) params.get("serverName");
        if(config==null || serverName==null)
            return ResponseBaseResult.failedResponseBaseResult.setMessage("配置信息为空");
        SandboxObject sandboxObject = sandboxMgr.getLoadedSandboxInstance(serverName);
        if( sandboxObject == null)
            return ResponseBaseResult.failedResponseBaseResult.setMessage("对应服务sandbox未启动");
//        config = URLEncoder.encode(config);
        HttpResult httpResult = repeaterModuleMgr.pushConfig(sandboxObject, config);
        if(httpResult.getCode() != 200){
            return ResponseBaseResult.failedResponseBaseResult.setMessage(httpResult.getBody());
        }
        return ResponseBaseResult.successResponseBaseResult.setMessage(httpResult.getBody());
    }
}
