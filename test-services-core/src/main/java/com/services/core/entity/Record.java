package com.services.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 录制信息
 * @TableName record
 */
@TableName(value ="record")
@Data
public class Record implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @TableField(value = "sandbox_id")
    private Integer sandboxId;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 录制时间
     */
    @TableField(value = "gmt_record")
    private Date gmtRecord;

    /**
     * 应用名： 暂时这里存储的是应用服务的进程id，也就是虽然
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
    @TableField(value = "host")
    private String host;

    /**
     * 链路追踪ID
     */
    @TableField(value = "trace_id")
    private String traceId;

    /**
     * 链路追踪ID
     */
    @TableField(value = "entrance_desc")
    private String entranceDesc;

    /**
     * 记录序列化信息
     */
    @TableField(value = "wrapper_record")
    private String wrapperRecord;

    /**
     * 请求参数JSON
     */
    @TableField(value = "request")
    private String request;

    /**
     * 返回值JSON
     */
    @TableField(value = "response")
    private String response;

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
        Record other = (Record) that;
        return this.getId() == null ? other.getId() == null : this.getId().equals(other.getId());
//        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
//            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
//            && (this.getGmtRecord() == null ? other.getGmtRecord() == null : this.getGmtRecord().equals(other.getGmtRecord()))
//            && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()))
//            && (this.getEnvironment() == null ? other.getEnvironment() == null : this.getEnvironment().equals(other.getEnvironment()))
//            && (this.getHost() == null ? other.getHost() == null : this.getHost().equals(other.getHost()))
//            && (this.getTraceId() == null ? other.getTraceId() == null : this.getTraceId().equals(other.getTraceId()))
//            && (this.getEntranceDesc() == null ? other.getEntranceDesc() == null : this.getEntranceDesc().equals(other.getEntranceDesc()))
//            && (this.getWrapperRecord() == null ? other.getWrapperRecord() == null : this.getWrapperRecord().equals(other.getWrapperRecord()))
//            && (this.getRequest() == null ? other.getRequest() == null : this.getRequest().equals(other.getRequest()))
//            && (this.getResponse() == null ? other.getResponse() == null : this.getResponse().equals(other.getResponse()));
    }

    @Override
    public int hashCode() {
        return getId().intValue();
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
//        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
//        result = prime * result + ((getGmtRecord() == null) ? 0 : getGmtRecord().hashCode());
//        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
//        result = prime * result + ((getEnvironment() == null) ? 0 : getEnvironment().hashCode());
//        result = prime * result + ((getHost() == null) ? 0 : getHost().hashCode());
//        result = prime * result + ((getTraceId() == null) ? 0 : getTraceId().hashCode());
//        result = prime * result + ((getEntranceDesc() == null) ? 0 : getEntranceDesc().hashCode());
//        result = prime * result + ((getWrapperRecord() == null) ? 0 : getWrapperRecord().hashCode());
//        result = prime * result + ((getRequest() == null) ? 0 : getRequest().hashCode());
//        result = prime * result + ((getResponse() == null) ? 0 : getResponse().hashCode());
//        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sandboxId=").append(sandboxId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtRecord=").append(gmtRecord);
        sb.append(", appName=").append(appName);
        sb.append(", environment=").append(environment);
        sb.append(", host=").append(host);
        sb.append(", traceId=").append(traceId);
        sb.append(", entranceDesc=").append(entranceDesc);
        sb.append(", wrapperRecord=").append(wrapperRecord);
        sb.append(", request=").append(request);
        sb.append(", response=").append(response);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}