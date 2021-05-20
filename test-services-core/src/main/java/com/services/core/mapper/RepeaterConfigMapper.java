package com.services.core.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.RepeaterConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.services.core.entity.RepeaterConfig
 */
public interface RepeaterConfigMapper extends BaseMapper<RepeaterConfig> {

    /**
     * 默认查询满足条件且未删除的所有数据
     * @param page
     * @param env
     * @param appName
     * @return
     */
    Page<RepeaterConfig> pageByParams (Page<RepeaterConfig> page,
                                       @Param(value = "env") String env,
                                       @Param(value = "appName") String appName);

    RepeaterConfig selectByPidAndPort (@Param(value = "serverPid") Integer serverPid,
                                       @Param(value = "sandboxPort") Integer sandboxPort);
}




