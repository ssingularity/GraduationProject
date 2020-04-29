package com.sjtu.project.processservice.domain.topologyImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sjtu.project.processservice.domain.DataSource;
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

    @Override
    public void addInput(Topology topology) {
        inputList.add(topology);
    }

    @Override
    @JsonIgnore
    public DataSource getTargetDataSource() {
        Assert.notNull( targetDataSourceId, "流程创建过程中，服务的目标数据源应该在被Get前就创建好");
        return new DataSource(targetDataSourceId);
    }

    @Override
    protected void selfStart(String processId) {
        log.info("启动Service节点");
        /*
        TODO: 1. 创建targetDataSource
              2. 创建Channel
              3. 获取每个Input的targetDatasource
              4. 注册Channel
         */
    }
}
