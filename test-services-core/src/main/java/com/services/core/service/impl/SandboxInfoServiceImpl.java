package com.services.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.services.core.entity.SandboxInfo;
import com.services.core.service.SandboxInfoService;
import com.services.core.mapper.SandboxInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class SandboxInfoServiceImpl extends ServiceImpl<SandboxInfoMapper, SandboxInfo>
implements SandboxInfoService {

    private static Logger log = LoggerFactory.getLogger(SandboxInfoService.class);

    @Resource
    private SandboxInfoMapper sandboxInfoMapper;

    @Override
    public Page<SandboxInfo> pageByParams(String env, String host, String serverName, Integer currentPage, Integer pageSize) {
        return sandboxInfoMapper.pageByParams(new Page<>(currentPage, pageSize), env, host, serverName);
    }

    @Override
    public SandboxInfo selectById(Integer id) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("id", id);
        return sandboxInfoMapper.selectOne(qw);
    }

    @Override
    public List<SandboxInfo> selectByHost(String host) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("host", host);
        return sandboxInfoMapper.selectList(qw);
    }

    @Override
    public List<SandboxInfo> selectByServerName(String serverName) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("serverName", serverName);
        return sandboxInfoMapper.selectList(qw);
    }


    @Override
    public List<SandboxInfo> getByMultiParams(SandboxInfo sandboxInfo) {
        QueryWrapper<SandboxInfo> qw = new QueryWrapper();
        Class cls = SandboxInfo.class;
        Field[] fields = cls.getDeclaredFields();
        for(Field field: fields){
            try {
                field.setAccessible(true);
                Object value = field.get(sandboxInfo);
                if(value != null && !(value instanceof Date) && !value.equals("")){
                    if(!field.getName().equals("serialVersionUID")){ //过滤掉
                        qw = qw.eq(field.getName(), value);
                    }
                    if(field.getName().equals("id")){
                        return sandboxInfoMapper.selectList(qw);
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("获取属性：{}的值失败; 忽略这个属性继续执行",field.getName(), e);
            }
        }
        return sandboxInfoMapper.selectList(qw);
    }

    @Override
    public List<SandboxInfo> groupByAppNameList() {
        QueryWrapper<SandboxInfo> query = new QueryWrapper<>();
        query.select("distinct serverName");
        return sandboxInfoMapper.selectList(query);
    }

    public List<SandboxInfo> getByMultiParams(SandboxInfo sandboxInfo, Boolean flag) {
        QueryWrapper<SandboxInfo> qw = new QueryWrapper();
        Class cls = SandboxInfo.class;
        Field[] fields = cls.getDeclaredFields();
        for(Field field: fields){
            try {
                field.setAccessible(true);
                Object value = field.get(sandboxInfo);
                if(value != null && !(value instanceof Date) && !value.equals("")){
                    if(!field.getName().equals("serialVersionUID")){ //过滤掉
                        qw = qw.eq(field.getName(), value);
                    }
                    if(field.getName().equals("id")){
                        return sandboxInfoMapper.selectList(qw);
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("获取属性：{}的值失败; 忽略这个属性继续执行",field.getName(), e);
            }
        }
        return sandboxInfoMapper.selectList(qw);
    }

}




