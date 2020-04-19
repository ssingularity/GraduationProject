package com.sjtu.project.coreservice.domain.topology;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sjtu.project.coreservice.domain.*;
import com.sjtu.project.coreservice.domain.fusionRule.FusionRule;
import com.sjtu.project.coreservice.domain.transformRule.TransformRule;
import com.sjtu.project.coreservice.util.ContextUtils;
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
        InputChannel inputChannel = ContextUtils.ctx.getBean(ChannelService.class)
                .createInputChannel(fusionRule, transformRuleList, id);
        for (Topology input : inputList) {
            input.registerChannel(inputChannel);
        }
        // 每一个服务在注册的时候会自动帮它生成一个OutputChannel
    }

    @Override
    public void registerChannel(InputChannel inputChannel) {
        getTargetDataSource().get(0)
                .register(inputChannel);
    }

    @JsonIgnore
    public List<DataSource> getTargetDataSource() {
        return ContextUtils.ctx.getBean(ServiceManagement.class)
                .getDataSourceOf(id);
    }
}
