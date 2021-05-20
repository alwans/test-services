package com.sandbox.aide.controller;

import com.test.bean.SandboxObject;
import com.test.util.http.HttpHandleFactory;
import com.test.util.http.HttpResult;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sandbox.aide.config.CoreUrlConfig.upload_port_url;
import static com.sandbox.aide.config.CoreUrlConfig.upload_sandbox_url;

/**
 * 向services上报数据
 * 目前好像只要上报当前服务使用的随机端口
 *
 *
 * Created by wl on 2021/4/20.
 */

public class UploadData {

    private final static Logger log = LoggerFactory.getLogger(UploadData.class);



//    @Value("${services.upload.port.url}")
//    private String upload_port_url;
//
//    @Value("${services.upload.sandbox.url}")
//    private String upload_sandbox_url;

    public void upload_port(ConfigurableApplicationContext ctx){
        Map<String,Integer> param = new HashMap<>(1);
        Environment environment = ctx.getEnvironment();
        param.put("port", Integer.parseInt(environment.getProperty("local.server.port")));
//        upload_port_url = environment.getProperty("services.upload.port.url");
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle()
                .sendPostRequestWithJSON(upload_port_url, param);
        if(httpResult.getCode() !=200 || httpResult.getBody4BaseResult().getCode()!= 1){
            log.error("upload port failed, res: {}, Service will be closed soon...", httpResult.getBody());
            ctx.close();
            return;
        }
        log.info("upload port successfully !!!", httpResult.getBody());
    }

    public void upload_sandbox_list(List<SandboxObject> list, ConfigurableApplicationContext ctx){
        Map<String, List> param = new HashMap<>(1);
        param.put("list", list);
//        Environment  environment = ctx.getEnvironment();
//        upload_sandbox_url = environment.getProperty("services.upload.sandbox.url");
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle()
                .sendPostRequestWithJSON(upload_sandbox_url, param);
        if(httpResult.getCode() != 200 || httpResult.getBody4BaseResult().getCode() != 1){
            log.error("upload sandbox list failed, res: {}", httpResult.getBody());
            ctx.close();
            return;
        }
        log.info("upload sandbox list successfully");
    }

}
