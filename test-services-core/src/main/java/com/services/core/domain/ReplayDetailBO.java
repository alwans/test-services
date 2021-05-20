package com.services.core.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReplayDetailBO extends BaseBO{

    private Date gmtCreate;

    private String appName;

    private String ip;

    private String environment;

    private String repeatId;

    private String traceId;

    private String response;

    private Boolean success;

    private Long cost;

    private ReplayStatus status;

    private RecordDetailBO record;

    private List<MockInvocationBO> mockInvocations;

    private List<DifferenceBO> differences;

}
