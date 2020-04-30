package com.sjtu.project.processservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/29 18:00
 */
@Document
@Data
public class Process {
    @Id
    String id;

    String name;

    String description;

    String owner;

    Topology topology;

    ProcessStatus status = ProcessStatus.STOPPED;

    public void start() {
        topology.startWithProcessId(id);
        this.status = ProcessStatus.RUNNING;
    }

    public void stop() {
        topology.stop();
        this.status = ProcessStatus.STOPPED;
    }
}
