package com.sandbox.aide.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sandbox.aide.SandboxMgr;
import com.sandbox.aide.VMHandle;
import com.test.bean.SandboxObject;
import com.test.util.http.HttpHandleFactory;
import com.test.util.http.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

import static com.sandbox.aide.config.CoreUrlConfig.pullSandboxUrl;


/**
 * 从services同步已开启的sandbox信息
 * 每次服务启动的时候，会去拉取一次
 *
 * Created by wl on 2021/4/22.
 */
public class PullData {

    private static Logger log = LoggerFactory.getLogger(PullData.class);

//    @Value("${services.pull.sandbox.url}")
//    private String pullSandboxUrl;

    public void pullSandboxList(ConfigurableApplicationContext ctx){
//        Environment environment = ctx.getEnvironment();
//        pullSandboxUrl = environment.getProperty("services.pull.sandbox.url");
        log.info("SandboxObject list start sync");
        List<SandboxObject> removeList = new ArrayList<>();
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle()
                .sendGetRequest(pullSandboxUrl);
        if(httpResult.getCode() != 200 || httpResult.getBody4BaseResult().getCode()!=1){
            log.error("pull sandbox list failed, res: {}",httpResult.getBody());
            ctx.close();
            return;
        }
        try{
            JSONObject json = JSON.parseObject(httpResult.getBody());
            JSONArray jsons = (JSONArray)json.get("data");
            if(null == jsons) return;
            for(Object j: jsons){
                SandboxObject sandboxObject = JSON.parseObject(j.toString(),SandboxObject.class);
                if(sandboxObject.getServerName().equals("") ||
                        sandboxObject.getServerPid()== 0){
                    log.error("SandboxObject exception: {}",sandboxObject);
                    ctx.close();
                    return;
                }
                int pid = VMHandle.getServerPid(sandboxObject.getServerName());
                //0：服务已停止运行 || 不相等：说明应用服务可能重启过；
                if(pid ==0 || pid != sandboxObject.getServerPid()){
                    removeList.add(sandboxObject);
                }
                SandboxMgr.loadedServerList.put(sandboxObject.getServerName(), sandboxObject);
            }
            if(removeList.size()>0){ //移除掉已经失效的sandbox数据
                new UploadData().upload_sandbox_list(removeList, ctx);
            }
            log.info("SandboxObject list sync successfully !!!");
        }catch (Exception e){
            log.error("Failed to get sandboxObject list ", e);
            ctx.close();
        }

    }
}
