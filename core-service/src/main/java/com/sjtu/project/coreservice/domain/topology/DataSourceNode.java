package com.sjtu.project.coreservice.domain.topology;

import com.sjtu.project.coreservice.domain.Constants;
import com.sjtu.project.coreservice.domain.DataSourceService;
import com.sjtu.project.coreservice.domain.InputChannel;
import com.sjtu.project.coreservice.domain.Topology;
import lombok.Data;

@Data
public class DataSourceNode extends Topology {
    @Override
    public void addInput(Topology topology) {
        throw new IllegalArgumentException("DataSource为叶节点,不能再有Input");
    }

    @Override
    protected void selfRun() {

    }

    @Override
    public void registerChannel(InputChannel inputChannel) {
        Constants.ctx.getBean(DataSourceService.class)
                .register(id, inputChannel);
    }
}
