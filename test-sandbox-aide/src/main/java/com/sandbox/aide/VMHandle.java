package com.sandbox.aide;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvmstat.monitor.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 查找对用服务的pid
 * Created by wl on 2021/4/20.
 */
public class VMHandle {
    private static Logger log = LoggerFactory.getLogger(VMHandle.class);

    public static int getServerPid(String serverName){
        Set<Integer> pids = new HashSet();
        try {
            MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost("localhost");
            Set<Integer> vmlist = monitoredHost.activeVms();
            for(Integer pid : vmlist){
                MonitoredVm vm = monitoredHost.getMonitoredVm(new VmIdentifier("//" + pid));
                String targetStr = MonitoredVmUtil.mainClass(vm, true);
                boolean flag = true;
                if(!targetStr.equals("org.apache.catalina.startup.Bootstrap")
                        && targetStr.contains(serverName)){
                        flag = true;
                        pids.add(pid);
                }
                if(flag){
                   targetStr = MonitoredVmUtil.jvmArgs(vm);
                   if(targetStr.contains(serverName)){
                       pids.add(pid);
                   }
                }
            }
        } catch (Exception e) {
            log.error("get {} pid is error, {}", serverName, e);
        }
        if(pids.size()<1){
            log.error("{} not match the corresponding process id, Please check if the service is started", serverName);
            return 0;
        }
        if(pids.size()>1){
            log.error("{} matches multiple pid: {}", serverName, pids);
            return 0;
        }
        return pids.iterator().next();
    }
}
