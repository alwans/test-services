package com.sandbox.aide;

import com.alibaba.fastjson.JSON;
import com.test.bean.SandboxObject;
import com.test.util.http.HttpHandleFactory;
import com.test.util.http.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * sandbox模块管理
 * Created by wl on 2021/4/22.
 */
@Component
public class ModuleMgr {

    private final static Logger log = LoggerFactory.getLogger(ModuleMgr.class);

    private static final List<String> MODULE_BLACK_LIST = new ArrayList<>();

    static {
        MODULE_BLACK_LIST.add("sandbox-info");
        MODULE_BLACK_LIST.add("sandbox-module-mgr");
        MODULE_BLACK_LIST.add("sandbox-control");
    }

//    private static ModuleMgr instance;
//    static {instance = new ModuleMgr();}

    private String mgrModuleID = "sandbox-module-mgr";

    public boolean avtiveModule(SandboxObject sandboxObject ,String moduleID){
        Map map = getLoadedModule(sandboxObject, moduleID);
        if(null == map){
            log.error("{} Not loaded",moduleID);
            return false;
        }
        if(map.get("isActivated").equals("ACTIVE")) return true;
        String url = SandboxConst.getSandboxUrl(sandboxObject, mgrModuleID, "active");
        log.info("request url: {}", url);
        Map<String,String> param = new HashMap<>(1);
        param.put("ids", moduleID);
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle().sendPostRequest(url, param);
        log.info("res body: {}", httpResult.getBody());
        if(httpResult.getBody().contains("module activated")){
            return true;
        }
        return false;
    }

    private Map getLoadedModule(SandboxObject sandboxObject, String moduleID){
        List<Map> list = getModuleList(sandboxObject);
        for(Map m: list){
            if(m.get("id").equals(moduleID)){
                return m;
            }
        }
        return null;
    }

    public boolean forzenModule(SandboxObject sandboxObject ,String moduleID){
        if(MODULE_BLACK_LIST.contains(moduleID)){
            log.info(moduleID+" ：管理模块不能被冻结");
            return false;
        }
        Map map = getLoadedModule(sandboxObject, moduleID);
        if(map!=null && map.get("isActivated").equals("FROZEN")) return true;
        String url = SandboxConst.getSandboxUrl(sandboxObject, mgrModuleID, "frozen");
        log.info("request url: {}", url);
        Map<String,String> param = new HashMap<>(1);
        param.put("ids", moduleID);
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle().sendPostRequest(url, param);
        log.info("res body: {}", httpResult.getBody());
        if(httpResult.getBody().contains("module frozen")){
            return true;
        }
        return false;
    }

    public List<Map> getModuleList(SandboxObject sandboxObject){
        String url = SandboxConst.getSandboxUrl(sandboxObject, mgrModuleID, "list");
        log.info("request url: {}",url);
        HttpResult httpResult = HttpHandleFactory.getDefaultHandle().sendGetRequest(url);
        return formatListData(httpResult.getBody());
    }

    public List<Map> formatListData(String listStr){
        List<Map> list = null;
        String lineSeparator = System.lineSeparator();
        if(listStr.contains(lineSeparator)){
            String[] a1 = listStr.split(lineSeparator);
             list = new ArrayList<>(a1.length);
            if(a1.length>1){
                for(String s: a1){
                    String[] a2 = s.split("\t");
                    int pos=0;
                    Map<String,String> map = new HashMap<>(7);
                    if(a2.length<7){continue;}
                    for(String s2: a2){
                        s2 = s2.trim();
                        if(s2.trim() !=""){
                            switch (pos){
                                case 0:
                                    map.put("id",s2); pos++; break;
                                case 1:
                                    map.put("isActivated",s2); pos++; break;
                                case 2:
                                    map.put("isLoaded",s2); pos++; break;
                                case 3:
                                    map.put("cCnt",s2); pos++; break;
                                case 4:
                                    map.put("mCnt",s2); pos++; break;
                                case 5:
                                    map.put("version",s2); pos++; break;
                                case 6:
                                    map.put("author",s2); pos++; break;
                                default:
                                    break;
                            }
                        }
                    }
                    list.add(map);
                }
            }
        }
        return null==list? Collections.emptyList(): list;
    }


    public void forceFlushAllModule(){}

    public void flushAllModule(){}

    public void unloadModule(){}

    public void getModuleInfo(String moduleMatchStr){}

    public void reloadModule(String moduleMatchStr){}

//    public static ModuleMgr getInstance(){
//        return instance;
//    }
}
