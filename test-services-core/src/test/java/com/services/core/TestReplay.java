package com.services.core;


import com.services.core.service.ReplayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestReplay {

    @Resource
    private ReplayService replayService;

    @Test
    public void testGroupByDate(){
        replayService.getAllBatchHistory();
    }
}
