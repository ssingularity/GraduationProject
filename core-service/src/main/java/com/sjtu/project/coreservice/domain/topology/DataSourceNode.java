package com.sjtu.project.coreservice.domain.topology;

public class DataSourceNode extends Topology{
    @Override
    void addInput(Topology topology) {
        throw new IllegalArgumentException("DataSource为叶节点,不能再有Input");
    }
}
