package com.sandbox.aide;

import com.test.bean.SandboxObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Created by wl on 2021/4/22.
 */
public class SandboxConst {

    private static SandboxConst instance;

    static {
       instance = new SandboxConst();
    }

    @Value("${sandbox.url}")
    private static String sandboxUrl;

    private static String localHost ="127.0.0.1";

    public static String getSandboxUrl(SandboxObject sandboxObject,
                                       String moduleId, String commondId){
        return String.format(sandboxUrl, localHost, sandboxObject.getSandboxPort(),
                sandboxObject.getSandboxNameSpace(), moduleId, commondId);
    }

    public static SandboxConst getInstance(){
        return instance;
    }

    public void setProperties(ConfigurableApplicationContext ctx){
        Environment environment = ctx.getEnvironment();
        sandboxUrl = environment.getProperty("sandbox.url");
    }
}
