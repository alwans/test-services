package com.test.bean;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * sandbox启动实例拥有的属性
 * Created by wl on 2021/4/21.
 */
public class SandboxObject {

    private final static String DEFAULT_NAMESPACE = "default";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getServerPid() {
        return serverPid;
    }

    public void setServerPid(int serverPid) {
        this.serverPid = serverPid;
    }

    public int getSandboxPort() {
        return sandboxPort;
    }

    public void setSandboxPort(int sandboxPort) {
        this.sandboxPort = sandboxPort;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSandboxNameSpace() {
        return sandboxNameSpace;
    }

    public void setSandboxNameSpace(String sandboxNameSpace) {
        this.sandboxNameSpace = sandboxNameSpace;
    }

    /**
     * 对应的服务id
     */
    private Long id;

    /*对应所在服务器的ip*/
    private String host;

    /*对应服务的名称*/
    private String serverName;

//    /*查找服务进程用的匹配字符串*/
//    private String matchStr;

    /*对应服务的进程id*/
    private int serverPid;

    /*对应nettyServer中的端口号*/
    private int sandboxPort;

    /*sandbox启动时候生成的token*/
    private String token;

    /*启动sandbox自定义的namespace*/
    private String sandboxNameSpace = DEFAULT_NAMESPACE;

}
