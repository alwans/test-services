package com.services.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.RepeaterConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface RepeaterConfigService extends IService<RepeaterConfig> {

    /**
     * 应用录制配置信息 分页查询
     * @param env
     * @param appName
     * @param current
     * @param pageSize
     * @return
     */
    Page<RepeaterConfig> pageByparams (String env, String appName, Integer current, Integer pageSize);

    /**
     * 忘了
     * @param serverPid
     * @param sandboxPort
     * @return
     */
    RepeaterConfig getConfigBo (Integer serverPid, Integer sandboxPort);

    RepeaterConfig getConfigBo (Integer id);

}
