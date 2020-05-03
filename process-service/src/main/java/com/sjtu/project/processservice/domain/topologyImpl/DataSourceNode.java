package com.sjtu.project.processservice.domain.topologyImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sjtu.project.processservice.domain.DataSource;
import com.sjtu.project.processservice.domain.Topology;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class DataSourceNode extends Topology {
    @Override
    public void addInput(Topology topology) {
        throw new IllegalArgumentException("DataSource为叶节点,不能再有Input");
    }

    @Override
    protected void selfStop() {
        log.info("停止DataSource节点");
    }

    @Override
    protected void selfStart(String processId) {
        log.info("启动DataSource节点");
    }

    @Override
    @JsonIgnore
    public DataSource getTargetDataSource() {
        return new DataSource(id);
    }
}
