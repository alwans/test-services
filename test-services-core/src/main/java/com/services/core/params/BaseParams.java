package com.services.core.params;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseParams implements Serializable {

    private Integer currentPage;

    private Integer pageSize;

    private String appName;


}
