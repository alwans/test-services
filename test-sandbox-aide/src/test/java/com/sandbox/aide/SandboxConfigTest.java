package com.sandbox.aide;


import com.alibaba.fastjson.JSON;
import com.test.bean.SandboxObject;
import org.junit.Test;

/**
 * Created by wl on 2021/4/22.
 */
public class SandboxConfigTest {

    @Test
    public void test(){
        String str = "{\"serverName\":\"sdf\",\"matchStr\":\"sdf\"}";

        SandboxObject sandboxObject = JSON.parseObject(str, SandboxObject.class);
        System.out.println(sandboxObject);
    }

}
