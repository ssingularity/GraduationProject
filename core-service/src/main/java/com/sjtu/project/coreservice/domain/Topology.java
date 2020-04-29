package com.sjtu.project.coreservice.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@Data
public abstract class Topology {
    protected Set<Topology> inputList = new HashSet<>();

    protected String id;

    protected String name;

    public abstract void addInput(Topology topology);

    public abstract void registerChannel(InputChannel inputChannel);

    protected abstract void selfStart(String processId);

    boolean isAccessorOf(Topology topology) {
        return getInputList().contains(topology);
    }

    public void startWithProccessId(String processId) {
        for (Topology input : inputList) {
            input.startWithProccessId(processId);
        }
        selfStart(processId);
    }
}
