package com.services.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 回放信息
 * @TableName replay
 */
@TableName(value ="replay")
@Data
public class Replay implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;

    /**
     * 应用名
     */
    @TableField(value = "app_name")
    private String appName;

    /**
     * 环境信息
     */
    @TableField(value = "environment")
    private String environment;

    /**
     * 机器IP
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 回放ID
     */
    @TableField(value = "repeat_id")
    private String repeatId;

    /**
     * 回放状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 链路追踪ID
     */
    @TableField(value = "trace_id")
    private String traceId;

    /**
     * 回放耗时
     */
    @TableField(value = "cost")
    private Long cost;

    /**
     * diff结果
     */
    @TableField(value = "diff_result")
    private String diffResult;

    /**
     * 回放结果
     */
    @TableField(value = "response")
    private String response;

    /**
     * mock过程
     */
    @TableField(value = "mock_invocation")
    private String mockInvocation;

    /**
     * 是否回放成功
     */
    @TableField(value = "success")
    private Boolean success;

    /**
     * 外键
     */
    @TableField(value = "record_id")
    private Long recordId;

    /**
     * 是否批量执行
     */
    @TableField(value = "batch")
    private Boolean batch;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Replay other = (Replay) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()))
            && (this.getEnvironment() == null ? other.getEnvironment() == null : this.getEnvironment().equals(other.getEnvironment()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getRepeatId() == null ? other.getRepeatId() == null : this.getRepeatId().equals(other.getRepeatId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTraceId() == null ? other.getTraceId() == null : this.getTraceId().equals(other.getTraceId()))
            && (this.getCost() == null ? other.getCost() == null : this.getCost().equals(other.getCost()))
            && (this.getDiffResult() == null ? other.getDiffResult() == null : this.getDiffResult().equals(other.getDiffResult()))
            && (this.getResponse() == null ? other.getResponse() == null : this.getResponse().equals(other.getResponse()))
            && (this.getMockInvocation() == null ? other.getMockInvocation() == null : this.getMockInvocation().equals(other.getMockInvocation()))
            && (this.getSuccess() == null ? other.getSuccess() == null : this.getSuccess().equals(other.getSuccess()))
            && (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
        result = prime * result + ((getEnvironment() == null) ? 0 : getEnvironment().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getRepeatId() == null) ? 0 : getRepeatId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTraceId() == null) ? 0 : getTraceId().hashCode());
        result = prime * result + ((getCost() == null) ? 0 : getCost().hashCode());
        result = prime * result + ((getDiffResult() == null) ? 0 : getDiffResult().hashCode());
        result = prime * result + ((getResponse() == null) ? 0 : getResponse().hashCode());
        result = prime * result + ((getMockInvocation() == null) ? 0 : getMockInvocation().hashCode());
        result = prime * result + ((getSuccess() == null) ? 0 : getSuccess().hashCode());
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", appName=").append(appName);
        sb.append(", environment=").append(environment);
        sb.append(", ip=").append(ip);
        sb.append(", repeatId=").append(repeatId);
        sb.append(", status=").append(status);
        sb.append(", traceId=").append(traceId);
        sb.append(", cost=").append(cost);
        sb.append(", diffResult=").append(diffResult);
        sb.append(", response=").append(response);
        sb.append(", mockInvocation=").append(mockInvocation);
        sb.append(", success=").append(success);
        sb.append(", recordId=").append(recordId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}