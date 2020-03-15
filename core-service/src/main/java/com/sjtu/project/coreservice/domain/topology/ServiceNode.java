package com.sjtu.project.coreservice.domain.topology;

public class ServiceNode extends Topology{
    @Override
    void addInput(Topology topology) {
        inputList.add(topology);
    }
}
