package com.services.core;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.SandboxInfo;
import com.services.core.service.SandboxInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wl on 2021/4/25.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSandboxInfo {
    @Autowired
    SandboxInfoService sandboxInfoService;

    @Test
    public void test4page(){
        Page<SandboxInfo> pages = sandboxInfoService.pageByParams(null, null, null, 1,1);
        System.out.println(pages.getRecords());
        System.out.println(pages.getSize());
        System.out.println(pages.getRecords().size());

    }

    @Test
    public void test4update(){
        SandboxInfo sandboxInfo = new SandboxInfo();
        sandboxInfo.setId(1l);
        sandboxInfo.setHost("192.168.101.33");
        boolean state = sandboxInfoService.saveOrUpdate(sandboxInfo);
        System.out.println(state);
    }

    @Test
    public void test4insert(){
        SandboxInfo sandboxInfo = new SandboxInfo();
        sandboxInfo.setEnv("perp");
        sandboxInfo.setHost("192.168.101.131");
        sandboxInfo.setServerName("message-event");
        boolean state = sandboxInfoService.saveOrUpdate(sandboxInfo);
        System.out.println(state);
    }



}
