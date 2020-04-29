package com.sjtu.project.coreservice.domain.topologyImpl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.coreservice.domain.ChannelServiceClient;
import com.sjtu.project.coreservice.domain.InputChannel;
import com.sjtu.project.coreservice.domain.ServiceManagementClient;
import com.sjtu.project.coreservice.domain.Topology;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonTypeName(value = "service")
@Slf4j
public class ServiceNode extends Topology {
    protected FusionRule fusionRule;

    protected List<TransformRule> transformRuleList = new ArrayList<>();

    @Override
    public void addInput(Topology topology) {
        inputList.add(topology);
    }

    @Override
    protected void selfStart(String processId) {
        log.info("启动Service节点");
        InputChannel inputChannel = ContextUtil.ctx.getBean(ChannelServiceClient.class)
                .createInputChannel(fusionRule, transformRuleList, id);
        for (Topology input : inputList) {
            input.registerChannel(inputChannel);
        }
    }

    @Override
    public void registerChannel(InputChannel inputChannel) {
        ContextUtil.ctx.getBean(ServiceManagementClient.class)
                .getDataSourceOf(id)
                .getData()
                .register(inputChannel);
    }
}
