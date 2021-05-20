package com.services.core.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.Replay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.services.core.entity.Replay
 */
public interface ReplayMapper extends BaseMapper<Replay> {

    Page<Replay> pageByParams(Page<Replay> page, String appName, String repeatId);

}




