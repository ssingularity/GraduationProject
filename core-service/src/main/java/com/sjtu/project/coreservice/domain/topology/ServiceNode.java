package com.sjtu.project.coreservice.domain.topology;

import com.sjtu.project.coreservice.domain.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServiceNode extends Topology {
    protected FusionRule fusionRule;

    protected List<TransformRule> transformRuleList = new ArrayList<>();

    @Override
    public void addInput(Topology topology) {
        inputList.add(topology);
    }

    @Override
    protected void selfRun() {
        InputChannel inputChannel = Constants.ctx.getBean(ChannelService.class)
                .createInputChannel(fusionRule, transformRuleList, id);
        for (Topology input : inputList) {
            input.registerChannel(inputChannel);
        }
        // 每一个服务在注册的时候会自动帮它生成一个OutputChannel
    }

    @Override
    public void registerChannel(InputChannel inputChannel) {
        DataSource ds = Constants.ctx.getBean(ServiceManagement.class).getTargetDataSource(id).get(0);
        Constants.ctx.getBean(DataSourceService.class).register(ds.getId(), inputChannel);
    }
}