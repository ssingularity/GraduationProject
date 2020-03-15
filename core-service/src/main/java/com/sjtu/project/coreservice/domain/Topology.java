package com.sjtu.project.coreservice.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
