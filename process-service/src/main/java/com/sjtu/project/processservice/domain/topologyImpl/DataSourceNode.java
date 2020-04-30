package com.sjtu.project.processservice.domain.topologyImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sjtu.project.processservice.dto.DataSourceDTO;
import com.sjtu.project.processservice.domain.Topology;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@JsonTypeName(value = "dataSource")
public class DataSourceNode extends Topology {
    @Override
    public void addInput(Topology topology) {
        throw new IllegalArgumentException("DataSource为叶节点,不能再有Input");
    }

    @Override
    protected void selfStart(String processId) {
        log.info("启动DataSource节点");
    }

    @Override
    @JsonIgnore
    public DataSourceDTO getTargetDataSource() {
        return new DataSourceDTO(id);
    }
}