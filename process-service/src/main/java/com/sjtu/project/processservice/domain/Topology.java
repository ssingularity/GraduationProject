package com.sjtu.project.processservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public abstract DataSource getTargetDataSource();

    protected abstract void selfStart(String processId);

    protected abstract void selfStop();

    boolean isAccessorOf(Topology topology) {
        return getInputList().contains(topology);
    }

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
