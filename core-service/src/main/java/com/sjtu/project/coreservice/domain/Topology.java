package com.sjtu.project.coreservice.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sjtu.project.coreservice.domain.topology.DataSourceNode;
import com.sjtu.project.coreservice.domain.topology.ServiceNode;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ServiceNode.class, name = "SERVICE"),
        @JsonSubTypes.Type(value = DataSourceNode.class, name = "DATASOURCE"),
})
@Data
public abstract class Topology {
    protected Set<Topology> inputList = new HashSet<>();

    protected String id;

    protected String name;

    public abstract void addInput(Topology topology);

    public abstract void registerChannel(InputChannel inputChannel);

    protected abstract void selfRun();

    boolean isAccessorOf(Topology topology) {
        return getInputList().contains(topology);
    }

    public void run() {
        for (Topology input : inputList) {
            input.run();
        }
        selfRun();
    }
}
