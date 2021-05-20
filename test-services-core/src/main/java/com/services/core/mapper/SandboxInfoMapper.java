package com.services.core.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.SandboxInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.services.core.entity.SandboxInfo
 */
public interface SandboxInfoMapper extends BaseMapper<SandboxInfo> {

    Page<SandboxInfo> pageByParams(Page<SandboxInfo> page,
                                   @Param(value = "env") String env,
                                   @Param(value = "host") String host,
                                   @Param(value = "serverName") String serverName);

}




