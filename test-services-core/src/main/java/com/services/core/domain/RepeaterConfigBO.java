package com.services.core.domain;


import lombok.Data;

import java.io.Serializable;

/**
 * 暂时不用了，直接Entity传到前端展示，不做数据转换了
 */
@Deprecated
@Data
public class RepeaterConfigBO implements Serializable {

    private Integer id;
    private Integer sandboxId;
    private String env;
    private String appName;
    private boolean useTtl;
    private boolean degrade;
    private String pluginsPath;
    private String[] repeatIdentities;
}
