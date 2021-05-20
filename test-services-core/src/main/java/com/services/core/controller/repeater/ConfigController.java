package com.services.core.controller.repeater;


import com.alibaba.fastjson.JSON;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.services.core.entity.RepeaterConfig;
import com.services.core.service.RepeaterConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @link repeater-console 移植过来的接口
 * 后期考虑由aide服务转发，不直接请求core-services
 */
@RestController
@RequestMapping("/facade/api")
public class ConfigController {

    @Resource
    private RepeaterConfigService repeaterConfigService;

    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    /**
     * 为了偷懒，启动应用服务的时候，不设置app.name 和 env
     *
     * @param appName 这里传过来的是：应用服务的进程id
     * @param env  这里传过来的是： sandbox netty服务的端口
     * @return
     */
    @RequestMapping(value = "/config/{appName}/{env}", method = RequestMethod.GET)
    public RepeaterResult<com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig> getConfig (@PathVariable("appName") Integer appName,
                                                                                                    @PathVariable("env") Integer env){
        RepeaterConfig repeaterConfig = repeaterConfigService.getConfigBo(appName, env);
        com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig config =
                JSON.parseObject(repeaterConfig.getConfig(), com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig.class);
        return RepeaterResult.builder().message("获取成功").success(true).data(config).build();
    }


}
