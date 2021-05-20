package com.services.core.util;

import javax.servlet.http.HttpServletRequest;

public class NetworkUtils {

    /**
     * 获取 请求源的ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request){
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
