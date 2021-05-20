package com.services.core.domain;

import lombok.Data;

import java.util.List;

/**
 * 返给web的replay config中的子调用
 */
@Data
public class SubInvokeBO extends BaseBO{

    private String className;

    private List<String> methods;


}
