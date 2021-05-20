package com.services.core.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.SerializerWrapper;
import com.services.core.entity.RepeaterConfig;
import com.services.core.entity.SandboxInfo;
import com.services.core.util.JacksonUtil;
import com.test.util.http.HttpHandleFactory;
import com.test.util.http.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 和aide服务交互
 * Created by wl on 2021/4/25.
 */
@Component
public class AideHandle {

    private static final Logger log = LoggerFactory.getLogger(AideHandle.class);

    private static String urlTemplate = "http://%s:%s/%s/%s";

    private static String sandboxMgrPath = "api/sandbox";

    private static String moduleMgrPath = "api/module";

    private static String moduleConfigPath = "api/module/config";

    private static String repeatModulePath = "/api/module/repeat";

    /**
     * 启动sandbox
     * @param sandboxInfo
     * @return
     */
    public HttpResult attachServer(SandboxInfo sandboxInfo){
        String url = String.format(urlTemplate, sandboxInfo.getHost(),
                sandboxInfo.getAideServerPort(), sandboxMgrPath, "attachServer");
        return HttpHandleFactory.getDefaultHandle()
                .sendPostRequestWithJSON(url, sandboxInfo);
    }

    /**
     * 关闭sandbox
     * @param sandboxInfo
     * @return
     */
    public HttpResult shutdownSandbox(SandboxInfo sandboxInfo){
        String url = String.format(urlTemplate, sandboxInfo.getHost(),
                sandboxInfo.getAideServerPort(), sandboxMgrPath, "shutdown");
        return HttpHandleFactory.getDefaultHandle()
                .sendPostRequestWithJSON(url, sandboxInfo);
    }

    /**
     * 获取所有模块信息
     * @param sandboxInfo
     * @return
     */
    public HttpResult getAllModule(SandboxInfo sandboxInfo){
        String url = String.format(urlTemplate, sandboxInfo.getHost(),
                sandboxInfo.getAideServerPort(), moduleMgrPath, "getModuleList");
        return HttpHandleFactory.getDefaultHandle()
                .sendPostRequestWithJSON(url, sandboxInfo);
    }

    /**
     * 更新模块状态
     * @param sandboxInfo
     * @param moduleId
     * @param state
     * @return
     */
    public HttpResult changeModuleSate(SandboxInfo sandboxInfo, String moduleId, boolean state){
        String url = String.format(urlTemplate, sandboxInfo.getHost(),
                sandboxInfo.getAideServerPort(), moduleMgrPath, "change/active/state");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sandboxInfo", sandboxInfo);
        jsonObject.put("moduleId", moduleId);
        jsonObject.put("state", state);
        return HttpHandleFactory.getDefaultHandle()
                .sendPostRequestWithJSON(url, jsonObject);
    }

    /**
     * 更新repeater录制配置信息
     * @param sandboxInfo
     * @param repeaterConfig
     * @return
     */
    public HttpResult pushRepeaterConfig(SandboxInfo sandboxInfo, RepeaterConfig repeaterConfig){
        String url = String.format(urlTemplate, sandboxInfo.getHost(),
                sandboxInfo.getAideServerPort(), moduleConfigPath, "push/repeater/config");
        String data;
        try {
            com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig config = JacksonUtil.deserialize(repeaterConfig.getConfig(), com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig.class);
            data = SerializerWrapper.hessianSerialize(config);
        } catch (SerializeException e) {
            log.error("serialize config occurred error, message = ", e);
            return new HttpResult(-1,"serialize config occurred error");
        }
        Map<String,String> params = new HashMap<>(2);
        params.put("config", data);
        params.put("serverName", sandboxInfo.getServerName());
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle().sendPostRequestWithJSON(url, params);
        return httpResult;
    }

    /**
     * 执行回放
     * @param sandboxInfo
     * @param params
     * @return
     */
    public HttpResult execSingleReplay(SandboxInfo sandboxInfo, Map<String,String> params){
        String url = String.format(urlTemplate, sandboxInfo.getHost(),
                sandboxInfo.getAideServerPort(), repeatModulePath, "exec/single/replay");
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle().sendPostRequestWithJSON(url, params);
        return httpResult;
    }



}
