package com.sjtu.project.processservice.domain.topologyImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sjtu.project.common.exception.ServiceException;
import com.sjtu.project.common.response.ResultCode;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.processservice.domain.ChannelServiceClient;
import com.sjtu.project.processservice.dto.DataSourceDTO;
import com.sjtu.project.processservice.dto.InputChannelDTO;
import com.sjtu.project.processservice.domain.ServiceManagementClient;
import com.sjtu.project.processservice.domain.Topology;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonTypeName(value = "service")
@Slf4j
public class ServiceNode extends Topology {
    protected FusionRule fusionRule;

    protected List<TransformRule> transformRuleList = new ArrayList<>();

    private String targetDataSourceId;

    private String channelId;

    private String processId;

    @Override
    public void addInput(Topology topology) {
        inputList.add(topology);
    }

    @Override
    @JsonIgnore
    public DataSourceDTO getTargetDataSource() {
        Assert.notNull( targetDataSourceId, "流程创建过程中，服务的目标数据源应该在被Get前就创建好");
        return new DataSourceDTO(targetDataSourceId);
    }

    @Override
    protected void selfStart(String processId) {
        log.info("启动Service节点");
        verifySelf();
        this.processId = processId;
        this.targetDataSourceId = createTargetDataSource().getId();
        this.channelId = createChannel().getId();
        registerChannel2InputList();
    }

    private void verifySelf() {
        if (targetDataSourceId != null || channelId != null) {
            throw new ServiceException(ResultCode.WRONG_STATE);
        }
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
                .transformRules(transformRuleList)
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
