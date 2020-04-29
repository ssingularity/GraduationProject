package com.sjtu.project.coreservice.domain;

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

    String owner;

    Topology topology;

    public void start() {
        topology.startWithProccessId(id);
    }
}
