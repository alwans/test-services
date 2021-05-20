package com.sandbox.aide.module;

import com.sandbox.aide.SandboxConst;
import com.sandbox.aide.SandboxMgr;
import com.sandbox.aide.util.Constants;
import com.test.bean.SandboxObject;
import com.test.util.http.HttpHandleFactory;
import com.test.util.http.HttpResult;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class RepeaterModuleMgr {

    private static final Logger log = LoggerFactory.getLogger(RepeaterModuleMgr.class);

    private String repeaterModuleID = "repeater";

    public HttpResult pushConfig(SandboxObject sandboxObject, String config){
        String url = SandboxConst.getSandboxUrl(sandboxObject, repeaterModuleID, "pushConfig");
        final Map<String,String> paramMap = new HashMap<>(2);
        paramMap.put(Constants.DATA_TRANSPORT_IDENTIFY,  config);
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle().sendGetRequest(url, paramMap);
        return httpResult;
    }

    public HttpResult execSingleReplay(SandboxObject sandboxObject, String data){
        String url = SandboxConst.getSandboxUrl(sandboxObject, repeaterModuleID, "repeat");
        final Map<String,String> paramMap = new HashMap<>(2);
        paramMap.put(Constants.DATA_TRANSPORT_IDENTIFY, data);
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle().sendPostRequest(url, paramMap);
        return httpResult;
    }

}
