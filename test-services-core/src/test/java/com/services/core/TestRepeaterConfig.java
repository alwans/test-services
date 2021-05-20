package com.services.core;

import com.services.core.entity.RepeaterConfig;
import com.services.core.service.RepeaterConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRepeaterConfig {

    @Autowired
    private RepeaterConfigService repeaterConfigService;

    @Test
    public void Test4Insert(){
        RepeaterConfig repeaterConfig = new RepeaterConfig();
        repeaterConfig.setEnv("test");
        repeaterConfig.setSandboxId(1);
        repeaterConfig.setAppName("jvmTest");
        repeaterConfig.setConfig("sdf");
        repeaterConfigService.save(repeaterConfig);
    }
}
