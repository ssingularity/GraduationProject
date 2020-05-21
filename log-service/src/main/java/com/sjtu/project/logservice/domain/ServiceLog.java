package com.sjtu.project.logservice.domain;


import lombok.Data;

@Data
public class ServiceLog extends Log{
    private String serviceId;

    private String serviceName;

    private Long executingQueueSize;

    private Long waitingQueueSize;
}
