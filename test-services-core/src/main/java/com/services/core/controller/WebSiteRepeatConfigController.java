package com.services.core.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.RepeaterConfig;
import com.services.core.entity.SandboxInfo;
import com.services.core.http.AideHandle;
import com.services.core.http.WebResponseBaseResult;
import com.services.core.service.RepeaterConfigService;
import com.services.core.service.SandboxInfoService;
import com.test.util.http.HttpResult;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * web页面 录制配置信息下相关接口
 */
@RestController
@RequestMapping("api/website/repeater")
public class WebSiteRepeatConfigController {

    private static final Logger log = LoggerFactory.getLogger(WebSiteRepeatConfigController.class);

    @Resource
    private RepeaterConfigService repeaterConfigService;

    @Resource
    private SandboxInfoService sandboxInfoService;

    @Resource
    private AideHandle aideHandle;

    /**
     * 获取应用录制配置的列表数据
     * @param env  查询参数
     * @param appName 查询参数
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/config/list", method = RequestMethod.GET)
    public ResponseBaseResult getRepeaterConfigList(@RequestParam String env,
                                                    @RequestParam String appName,
                                                    @RequestParam Integer currentPage,
                                                    @RequestParam Integer pageSize){
        currentPage = currentPage != null && currentPage !=0? currentPage: 1;
        pageSize = pageSize != null && pageSize !=0? pageSize: 10;
        Page<RepeaterConfig> pages = repeaterConfigService.pageByparams(env, appName, currentPage, pageSize);
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
     * 更新|新增 应用录制配置信息
     * @param repeaterConfig
     * @return
     */
    @RequestMapping(value = "/config/inserOrUpdate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBaseResult repeaterConfigInsertOrUpdate(@RequestBody RepeaterConfig repeaterConfig){
        Page<RepeaterConfig> pages = repeaterConfigService.pageByparams(repeaterConfig.getEnv(),
                repeaterConfig.getAppName(), 1, 1);
        if(repeaterConfig.getId() != null && pages.getSize()>0
                && pages.getRecords().get(0).getId() != repeaterConfig.getId()){//这里是更新校验
            return ResponseBaseResult.failedResponseBaseResult.setMessage("环境信息和应用名配置已存在，请求修改再提交");
        }else if(repeaterConfig.getId() == null && pages.getTotal()>0){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("环境信息和应用名配置已存在，请求修改再提交");
        }
        boolean state = repeaterConfigService.saveOrUpdate(repeaterConfig);
        if(state){
            return ResponseBaseResult.successResponseBaseResult.setMessage("successfully");
        }
        return ResponseBaseResult.failedResponseBaseResult.setMessage("提交失败");
    }

    /**
     * 向sandbox推送更新录制配置信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/config/push",method = RequestMethod.GET)
    public ResponseBaseResult configPush(@RequestParam Integer id){
        if(id==null || id==0){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("id不能为空");
        }
        RepeaterConfig repeaterConfig = repeaterConfigService.getConfigBo(id);
        if(repeaterConfig == null){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("id对应的配置文件不存在");
        }
        SandboxInfo sandboxInfo =  sandboxInfoService.selectById(repeaterConfig.getSandboxId());
        HttpResult httpResult = aideHandle.pushRepeaterConfig(sandboxInfo, repeaterConfig);
        if(httpResult.getCode() != 200  || httpResult.getBody4BaseResult().getCode() !=1){
            return ResponseBaseResult.failedResponseBaseResult.setMessage("推送失败"+httpResult.getBody().toString());
        }
        return ResponseBaseResult.successResponseBaseResult.setMessage("推送成功");
    }
}
