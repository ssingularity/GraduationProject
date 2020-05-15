package com.sjtu.project.processservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sjtu.project.processservice.domain.topologyImpl.DataSourceNode;
import com.sjtu.project.processservice.domain.topologyImpl.ServiceNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ServiceNode.class, name = "Service"),
        @JsonSubTypes.Type(value = DataSourceNode.class, name = "DataSource"),
})
@Data
public abstract class Topology {
    protected List<Topology> inputList = new ArrayList<>();

    protected String id;

    protected String name;

    protected int[] value = new int[2];

    @JsonIgnore
    public abstract DataSource getTargetDataSource();

    protected abstract void selfStart(String processId);

    protected abstract void selfStop();

    public void startWithProcessId(String processId) {
        for (Topology input : inputList) {
            input.startWithProcessId(processId);
        }
        selfStart(processId);
    }

    public void stop() {
        selfStop();
        for (Topology input : inputList) {
            input.stop();
        }
    }
}
