package com.services.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.services.core.entity.SandboxInfo;
import com.services.core.service.SandboxInfoService;
import com.test.util.http.ResponseBaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.test.util.JsonUtils.objectConverion;

/**
 * Created by wl on 2021/4/23.
 * aide服务的请求处理
 */
@RestController
@RequestMapping("/api")
public class SandboxAideController {

    private static Logger log = LoggerFactory.getLogger(SandboxAideController.class);

    @Resource
    private SandboxInfoService sandboxInfoService;

    /**
     * 有一个问题，如果先启动aide服务，再新增对应的sandbox记录，这时候新增的都没aide服务端口
     * 上报端口相关信息放另一张表就没这个问题了，以后再说
     * @param request
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/upload/port", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBaseResult uploadPort(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        if(jsonObject.get("port")==null){
            return ResponseBaseResult.failedResponseBaseResult().setMessage("port参数不能为空");
        }
        String ipAddress = getIpAddress(request);
        if (ipAddress == null) {
            return ResponseBaseResult.failedResponseBaseResult().setMessage("未获取到对应的SERVER IP,无法完成端口绑定");
        }
        //更新数据<表设计的时候没考虑到，同一台服务N个应用，导致每次需要更新N个应用的aide服务的port>
        List<SandboxInfo> list = sandboxInfoService.selectByHost(ipAddress);
        if(list.size()==0){ //后面在优化 //这里为0,说明这个host没有新增sangbox_info记录，导致无法更新aide port；只好按失败处理
            log.info("{} 没有对应的服务，aide 端口不需要更新", ipAddress);
            return ResponseBaseResult.failedResponseBaseResult.setMessage(ipAddress+"没有对应的服务");
        }
        for(SandboxInfo sandboxInfo: list){
            sandboxInfo.setAideServerPort((Integer) jsonObject.get("port"));
        }
        boolean state= sandboxInfoService.saveOrUpdateBatch(list);
        if(!state){
            log.error("端口更新失败");
        }
        return ResponseBaseResult.successResponseBaseResult().setMessage("端口上报成功");
    }

    /**
     * aide服务来拉取对应服务器上的所有已启动sandbox的sandboxInfo信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/pull/sandbox/list", method = RequestMethod.GET)
    public ResponseBaseResult pullSandboxList(HttpServletRequest request){
        String ipAddress = getIpAddress(request);
        if (ipAddress == null) {
            return ResponseBaseResult.failedResponseBaseResult().setMessage("来自未知的请求,无法获取到SERVER IP");
        }
        List<SandboxInfo> list = sandboxInfoService.selectByHost(ipAddress);
        List<SandboxInfo> new_list = new ArrayList<>();
        if(list.size()>0){
            for(SandboxInfo sandboxInfo: list){
                if(null != sandboxInfo.getSandboxStatus() && sandboxInfo.getSandboxStatus()){
                    new_list.add(sandboxInfo);
                }
            }
        }
        return ResponseBaseResult.successResponseBaseResult().setData(new_list).setMessage("拉取sandbox列表成功");
    }

    /**
     * aide服务同步sandboxInfo信息中，发现端口不一致的数据，sandboxStatus状态更新为false
     * @param request
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/upload/sandbox/removeList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBaseResult removeSandboxList(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        String ipAddress = getIpAddress(request);
        if (ipAddress == null) {
            return ResponseBaseResult.failedResponseBaseResult().setMessage("来自未知的请求,无法获取到SERVER IP");
        }
        List list =  (List) jsonObject.get("list");
        for(Object o: list){
            SandboxInfo sandboxInfo = (SandboxInfo) objectConverion(o, SandboxInfo.class);
            sandboxInfo.setSandboxStatus(false);
            //更新sandbox状态
            boolean state = sandboxInfoService.saveOrUpdate(sandboxInfo);
            if(!state){
                log.error("{} sandbox 已关闭，状态更新失败", sandboxInfo.getServerName());
            }
        }
        return ResponseBaseResult.successResponseBaseResult().setMessage("同步sandbox列表成功");
    }


    /**
     * 获取 请求源的ip地址; 先这么暴力解决，反正不是最优解
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress != null) return ipAddress;

        ipAddress = request.getHeader("Proxy-Client-IP");
        if(ipAddress != null) return ipAddress;

        ipAddress = request.getHeader("WL-Proxy-Client-IP");
        if(ipAddress != null) return ipAddress;

        ipAddress = request.getRemoteAddr();
        return ipAddress;
    }

}
