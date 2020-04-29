package com.sjtu.project.coreservice.domain.topologyImpl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.coreservice.domain.DataSourceServiceClient;
import com.sjtu.project.coreservice.domain.InputChannel;
import com.sjtu.project.coreservice.domain.Topology;
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
    public void registerChannel(InputChannel inputChannel) {
        ContextUtil.ctx.getBean(DataSourceServiceClient.class)
                .register(id, inputChannel);
    }
}
