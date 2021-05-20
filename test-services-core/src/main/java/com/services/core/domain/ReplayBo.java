package com.services.core.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReplayBo extends BaseBO implements Serializable {

    /**
     * 流量入口
     */
    private String entranceDesc;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 环境信息
     */
    private String environment;

    /**
     * 机器IP
     */
    private String ip;

    /**
     * 回放ID
     */
    private String repeatId;

    /**
     * 回放状态
     */
    private Integer status;

    /**
     * 链路追踪ID
     */
    private String traceId;

    /**
     * 回放耗时
     */
    private Long cost;


    /**
     * 回放结果
     */
    private String response;

    /**
     * 是否回放成功
     */
    private Boolean success;

    /**
     * 外键
     */
    private Long recordId;

}
