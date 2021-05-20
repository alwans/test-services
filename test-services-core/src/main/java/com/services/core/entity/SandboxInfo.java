package com.services.core.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @TableName sandbox_info
 */
@Data
@TableName(value ="sandbox_info")
public class SandboxInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用对应的环境,例:dev;test;release
     */
    @TableField(value = "env")
    private String env;

    /**
     * 应用所在的机器ip
     */
    @TableField(value = "host")
    private String host;

    /**
     * 应用名称
     */
    @TableField(value = "serverName")
    private String serverName;

    /**
     * 应用进程id
     */
    @TableField(value = "serverPid")
    private Integer serverPid;

    /**
     * test-sandbox-aide服务的端口号
     */
    @TableField(value = "aideServerPort")
    private Integer aideServerPort;

    /**
     * sandbox的nameSpace
     */
    @TableField(value = "sandboxNameSpace")
    private String sandboxNameSpace;

    /**
     * sandbox中nettyServer端口
     */
    @TableField(value = "sandboxPort")
    private Integer sandboxPort;

    /**
     * sandbox启动状态:0(未启动) |  1(已启动)
     */
    @TableField(value = "sandboxStatus")
    private Boolean sandboxStatus;

    /**
     * sandbox的token
     */
    @TableField(value = "token")
    private String token;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "modifyTime")
    private Date modifyTime;

    /**
     * 操作人
     */
    @TableField(value = "operator")
    private String operator;

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
        SandboxInfo other = (SandboxInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEnv() == null ? other.getEnv() == null : this.getEnv().equals(other.getEnv()))
            && (this.getHost() == null ? other.getHost() == null : this.getHost().equals(other.getHost()))
            && (this.getServerName() == null ? other.getServerName() == null : this.getServerName().equals(other.getServerName()))
            && (this.getServerPid() == null ? other.getServerPid() == null : this.getServerPid().equals(other.getServerPid()))
            && (this.getAideServerPort() == null ? other.getAideServerPort() == null : this.getAideServerPort().equals(other.getAideServerPort()))
            && (this.getSandboxNameSpace() == null ? other.getSandboxNameSpace() == null : this.getSandboxNameSpace().equals(other.getSandboxNameSpace()))
            && (this.getSandboxPort() == null ? other.getSandboxPort() == null : this.getSandboxPort().equals(other.getSandboxPort()))
            && (this.getSandboxStatus() == null ? other.getSandboxStatus() == null : this.getSandboxStatus().equals(other.getSandboxStatus()))
            && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEnv() == null) ? 0 : getEnv().hashCode());
        result = prime * result + ((getHost() == null) ? 0 : getHost().hashCode());
        result = prime * result + ((getServerName() == null) ? 0 : getServerName().hashCode());
        result = prime * result + ((getServerPid() == null) ? 0 : getServerPid().hashCode());
        result = prime * result + ((getAideServerPort() == null) ? 0 : getAideServerPort().hashCode());
        result = prime * result + ((getSandboxNameSpace() == null) ? 0 : getSandboxNameSpace().hashCode());
        result = prime * result + ((getSandboxPort() == null) ? 0 : getSandboxPort().hashCode());
        result = prime * result + ((getSandboxStatus() == null) ? 0 : getSandboxStatus().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", env=").append(env);
        sb.append(", host=").append(host);
        sb.append(", serverName=").append(serverName);
        sb.append(", serverPid=").append(serverPid);
        sb.append(", aideServerPort=").append(aideServerPort);
        sb.append(", sandboxNameSpace=").append(sandboxNameSpace);
        sb.append(", sandboxPort=").append(sandboxPort);
        sb.append(", sandboxStatus=").append(sandboxStatus);
        sb.append(", token=").append(token);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", operator=").append(operator);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}