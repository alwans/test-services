package com.services.core.domain;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Invocation;
import com.services.core.entity.SandboxInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
public class ReplayConfigBO implements Serializable {

    /**
     * 回放环境
     */
    private String appName;

    /**
     * 是否使用录制机器
     */
    private boolean useRecordHost;

    /**
     * 需要回放的record id
     */
    private long record_id;

    /**
     * 生成的repeat_id，和tarce_id一样
     */
    private String repeat_id;

    /**
     * 被选中的录制机器对应的sandbox_info id
     */
    private int selectedHost;

    /**
     * record对应的录制机器的sandbox_info表的id
     */
    private int origin_sandbox_id;

    /**
     * 所有部署同一个应用所在的应用服务器集合
     */
    private List<SandboxInfo> hostList;

    /**
     * 是否需要mock
     */
    private boolean enableMock;

    /**
     * 是否mock全部子调用
     */
    private boolean enableAllMock;

    /**
     * 子调用集合
     * map：单个子调用，包括3个属性：【className:类名,methods:[name:xx] 】
     */
    private List<SubInvokeBO> subInvokeList;

    /**
     * 需要mock的子调用集合
     */
    private List<Invocation> needMockSubInvokeList;

    /**
     * 是否批量
     */
    private Boolean batch;

    /**
     * 回放sandbox对象
     */
    private SandboxInfo sandboxInfo;

}
