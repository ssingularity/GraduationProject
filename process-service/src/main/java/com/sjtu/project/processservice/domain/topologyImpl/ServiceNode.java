package com.sjtu.project.processservice.domain.topologyImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sjtu.project.common.exception.ServiceException;
import com.sjtu.project.common.response.ResultCode;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.processservice.domain.*;
import com.sjtu.project.processservice.domain.topologyImpl.fusionRule.FusionRule;
import com.sjtu.project.processservice.dto.DataSourceDTO;
import com.sjtu.project.processservice.dto.InputChannelDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class ServiceNode extends Topology {
    protected FusionRule fusionRule;

    protected List<TransformRule> transformRuleList = new ArrayList<>();

    private String targetDataSourceId;

    private Integer threshold;

    private String channelId;

    private String processId;

    @Override
    @JsonIgnore
    public DataSource getTargetDataSource() {
        Assert.notNull(targetDataSourceId, "流程开始与结束过程中，服务的目标数据源应该在被Get前就创建好");
        return new DataSource(targetDataSourceId);
    }

    @Override
    protected void selfStop() {
        log.info("停止Service节点");
        if (targetDataSourceId == null || channelId == null) {
            throw new ServiceException(ResultCode.WRONG_STATE);
        }
        unregisterChannel();
        deleteChannel();
        deleteTargetDataSource();
    }

    private void unregisterChannel() {
        for (Topology topology : inputList) {
            topology.getTargetDataSource().unregister(channelId);
        }
    }

    private void deleteChannel() {
        ContextUtil.ctx.getBean(ChannelServiceClient.class).deleteInputChannel(channelId);
        this.channelId = null;
    }

    private void deleteTargetDataSource() {
        ContextUtil.ctx.getBean(DataSourceServiceClient.class).deleteDataSource(targetDataSourceId);
        this.targetDataSourceId = null;
    }

    @Override
    protected void selfStart(String processId) {
        log.info("启动Service节点");
        if (targetDataSourceId != null || channelId != null) {
            throw new ServiceException(ResultCode.WRONG_STATE);
        }
        this.processId = processId;
        this.targetDataSourceId = createTargetDataSource().getId();
        this.channelId = createChannel().getId();
        registerChannel2InputList();
    }

    private DataSourceDTO createTargetDataSource() {
        return ContextUtil.ctx.getBean(ServiceManagementClient.class)
                .createDataSource(id)
                .getData();
    }

    private InputChannelDTO createChannel() {
        InputChannelDTO channelDTO = InputChannelDTO.builder()
                .fusionRule(fusionRule)
                .processId(processId)
                .targetDataSourceId(targetDataSourceId)
                .targetServiceId(id)
                .targetServiceName(name)
                .transformRules(transformRuleList)
                .threshold(threshold)
                .build();
        return ContextUtil.ctx.getBean(ChannelServiceClient.class)
                .createInputChannel(channelDTO)
                .getData();
    }

    private void registerChannel2InputList() {
        InputChannelDTO channelDTO = InputChannelDTO.builder().id(channelId).build();
        inputList.stream()
                .map(Topology::getTargetDataSource)
                .forEach(x -> x.register(channelDTO));
    }
}
