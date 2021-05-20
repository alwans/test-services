package com.services.core.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.services.core.entity.RepeaterConfig;
import com.services.core.service.RepeaterConfigService;
import com.services.core.mapper.RepeaterConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class RepeaterConfigServiceImpl extends ServiceImpl<RepeaterConfigMapper, RepeaterConfig>
implements RepeaterConfigService {

    @Resource
    private RepeaterConfigMapper repeaterConfigMapper;

    @Override
    public Page<RepeaterConfig> pageByparams(String env, String appName, Integer currentPage, Integer pageSize) {
        return repeaterConfigMapper.pageByParams(new Page<>(currentPage, pageSize), env, appName);
    }

    @Override
    public RepeaterConfig getConfigBo(Integer serverPid, Integer sandboxPort) {
        return repeaterConfigMapper.selectByPidAndPort(serverPid, sandboxPort);
    }

    @Override
    public RepeaterConfig getConfigBo(Integer id) {
        return repeaterConfigMapper.selectById(id);
    }
}




