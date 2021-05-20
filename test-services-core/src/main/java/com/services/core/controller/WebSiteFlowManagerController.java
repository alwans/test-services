package com.services.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.Record;
import com.services.core.http.WebResponseBaseResult;
import com.services.core.service.RecordService;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * web页面 流量相关接口
 */
@RestController
@RequestMapping("api/website/repeater/flow")
public class WebSiteFlowManagerController {

    private static final Logger log = LoggerFactory.getLogger(WebSiteFlowManagerController.class);

    @Resource
    private RecordService recordService;


    /**
     * 获取流量列表数据
     * @param currentPage
     * @param pageSize
     * @param appName
     * @param traceId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBaseResult flowList(@RequestParam int currentPage,
                                       @RequestParam int pageSize,
                                       @RequestParam  String appName,
                                       @RequestParam String traceId){
        pageSize = pageSize!=0? pageSize: 10;
        currentPage = currentPage !=0? currentPage: 1;
        Page<Record> pages = recordService.pageByParams(appName, traceId, currentPage, pageSize);
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
     * 获取流量详细信息
     * @param id  record_id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseBaseResult flowDetail(@RequestParam int id){
        if(id ==0) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不能为空");
        Record record = recordService.getById(id);
        if(record==null) return ResponseBaseResult.failedResponseBaseResult.setMessage("id不存在");
        return ResponseBaseResult.successResponseBaseResult().setData(recordService.getDetail(id)).setMessage("successfully");
    }






}
