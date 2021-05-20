package com.services.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.services.core.entity.SandboxInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SandboxInfoService extends IService<SandboxInfo> {
    /**
     * 根据条件查询sandbox_info列表
     * @param env
     * @param host
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<SandboxInfo> pageByParams(String env, String host, String serverName, Integer currentPage, Integer pageSize);

    /**
     * 根据id查询sandbox_info列表
     * @param id
     * @return
     */
    SandboxInfo selectById(Integer id);

    List<SandboxInfo> selectByHost(String host);

    List<SandboxInfo> selectByServerName(String serverName);


    /**
     * 根据多个属性来查询，但是id有值，那么只根据id查询；没有id，会结合其他有属性来查询(时间字段除外)
     * @param sandboxInfo
     * @return
     */
    List<SandboxInfo> getByMultiParams(SandboxInfo sandboxInfo);

    List<SandboxInfo> groupByAppNameList();


}
