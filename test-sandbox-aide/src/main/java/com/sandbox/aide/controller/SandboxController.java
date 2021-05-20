package com.sandbox.aide.controller;

import com.sandbox.aide.VMHandle;
import com.test.util.GaStringUtils;
import com.sandbox.aide.SandboxMgr;
import com.test.bean.SandboxObject;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 负责处理services发出的请求
 * Created by wl on 2021/4/20.
 */
@RestController
@RequestMapping("/api/sandbox")
public class SandboxController {

    private static Logger log = LoggerFactory.getLogger(SandboxController.class);

    @RequestMapping(value = "/attachServer", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseBaseResult attachServer(@RequestBody SandboxObject sandboxObject){
       return startSandbox(sandboxObject);
    }

    public ResponseBaseResult startSandbox(SandboxObject sandboxObject){
        if(GaStringUtils.isEmpty(sandboxObject.getServerName())){
            return ResponseBaseResult.failedResponseBaseResult().setMessage("serverName 不能为空");
        }
        int pid = VMHandle.getServerPid(sandboxObject.getServerName());
        if(pid!=0){
            sandboxObject.setServerPid(pid);
            boolean flag = SandboxMgr.getInstance().startSandboxServer(sandboxObject);
            if(!flag) return ResponseBaseResult.failedResponseBaseResult()
                    .setMessage(sandboxObject.getServerName()+ " 启动sandbox失败");

            int sandboxPort = SandboxMgr.getInstance().getSandboxPort(sandboxObject.getToken());
            if(sandboxPort==0) return ResponseBaseResult.failedResponseBaseResult().setMessage("未获取到sandbox的通信端口");
            sandboxObject.setSandboxPort(sandboxPort);
            return ResponseBaseResult.successResponseBaseResult().setData(sandboxObject).setMessage("sandbox 启动成功");
        }
        return ResponseBaseResult.failedResponseBaseResult().setMessage("未匹配到"+sandboxObject.getServerName()+" 服务的进程id");
    }

    @RequestMapping(value = "/shutdown",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBaseResult shutdownSandbox(@RequestBody SandboxObject sandboxObject){
        if(GaStringUtils.isEmpty(sandboxObject.getServerName())){
            return ResponseBaseResult.failedResponseBaseResult().setMessage("serverName 不能为空");
        }
        SandboxObject loadedInstace = SandboxMgr.getInstance().getLoadedSandboxInstance(sandboxObject.getServerName());
        if(null == loadedInstace){
            return ResponseBaseResult.failedResponseBaseResult().setMessage(sandboxObject.getServerName()
                    +"中的sandbox服务未启动,请先启动sandbox服务");
        }
        String s = SandboxMgr.getInstance().stopSandboxServer(loadedInstace);
        return ResponseBaseResult.successResponseBaseResult().setData(sandboxObject).setMessage(s);
    }

    @RequestMapping(value = "/getSandboxList",method = RequestMethod.GET)
    public ResponseBaseResult getSandboxList(){
        Map<String, Map> map = new HashMap<>(1);
        map.put("list", SandboxMgr.loadedServerList);
        return ResponseBaseResult.successResponseBaseResult().setData(map);
    }

}
