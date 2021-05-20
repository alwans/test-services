package com.services.core.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
public class BatchHistoryBO extends BaseBO{

    /**
     * 批量回放执行时间
     */
    private Date gmtCreate;

    /**
     * 批量执行流量总数
     */
    private int total;

    /**
     * 成功个数
     */
    private int successTotal;

    /**
     * 失败个数
     */
    private int failedTotal;

    /**
     * 批量执行的环境
     */
    private String environment;

    /**
     * 批量回放的机器
     */
    private String host;

    /**
     * 应用名
     */
    private String appName;


}
