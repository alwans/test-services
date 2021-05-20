package com.sandbox.aide;

import com.test.util.GaStringUtils;
import com.sun.tools.attach.VirtualMachine;
import com.test.bean.SandboxObject;
import com.test.util.http.HttpHandleFactory;
import com.test.util.http.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * sandbox管理
 * Created by wl on 2021/4/20.
 */
@Component
public class SandboxMgr {

    /*已加载sandbox的server列表*/
    public final static Map<String, SandboxObject> loadedServerList = new ConcurrentHashMap<>();

    private final static SandboxMgr instance;

    private String controlModuleID = "sandbox-control";

    static {
        instance = new SandboxMgr();
    }

    private final static Logger log = LoggerFactory.getLogger(SandboxMgr.class);
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock(true);


    /*只执行一次初始化，不管初始化会不会失败，因为失败了再尝试也不会成功*/
    public boolean initSandbox(){
        return FileHandle.copySandbox() && FileHandle.copyUserModule();
    }

    public boolean startSandboxServer(SandboxObject sandboxObject){
        if(loadedServerList.containsKey(sandboxObject.getServerName())){
            return true;
        }
        int pid = sandboxObject.getServerPid();
        rwLock.readLock().lock();
        try{
            new CoreLauncher(pid+"", FileHandle.AGENT_JAR_PATH, getConfigStr(sandboxObject));
            loadedServerList.put(sandboxObject.getServerName(), sandboxObject);
        } catch (Exception e) {
            log.error("Failed to start sandbox",e);
            return false;
        } finally {
            rwLock.readLock().unlock();
        }
        return true;
    }

    public String stopSandboxServer(SandboxObject sandboxObject){
        String url = SandboxConst.getSandboxUrl(sandboxObject, controlModuleID, "shutdown");
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle().sendGetRequest(url);
        loadedServerList.remove(sandboxObject.getServerName());
        return httpResult.getBody();
    }

    public void getVersionInfo(){

    }

    public SandboxObject getLoadedSandboxInstance(String serverName){
        return loadedServerList.get(serverName);
    }


    public static SandboxMgr getInstance(){
        return instance;
    }

    private String getToken(SandboxObject sandboxObject){
        if(GaStringUtils.isEmpty(sandboxObject.getServerName())){
            return "default".hashCode() + String.valueOf(System.currentTimeMillis());
        }
        String token = sandboxObject.getServerName().hashCode() + String.valueOf(System.currentTimeMillis());
        sandboxObject.setToken(token);
        return token;
    }

    public int getSandboxPort(String token){
        List<String> list = FileHandle.readLineFile(FileHandle.TOKEN_FILE_PATH);
        for(String str : list){
            if(str.contains(token)){
                List<String> paramsList = Arrays.asList(str.split(";"));
                if(paramsList.size()>0){
                    return Integer.parseInt(paramsList.get(paramsList.size()-1));
                }
            }
        }
        return 0;
    }

    private String getConfigStr(SandboxObject sandboxObject){
        String s = "home="+ FileHandle.HOME_SANDBOX_PATH+";token="
                +getToken(sandboxObject)+";server.ip=0.0.0.0;server.port=0;namespace="
                +sandboxObject.getSandboxNameSpace();
        return s;
    }

    class CoreLauncher{
        public CoreLauncher(final String targetJvmPid,
                            final String agentJarPath,
                            final String token) throws Exception {

            // 加载agent
            attachAgent(targetJvmPid, agentJarPath, token);

        }
        // 加载Agent
        private void attachAgent(final String targetJvmPid,
                                 final String agentJarPath,
                                 final String cfg) throws Exception {
            VirtualMachine vmObj = null;
            try {

                vmObj = VirtualMachine.attach(targetJvmPid);
                if (vmObj != null) {
                    vmObj.loadAgent(agentJarPath, cfg);
                }

            } finally {
                if (null != vmObj) {
                    vmObj.detach();
                }
            }

        }
    }
}
