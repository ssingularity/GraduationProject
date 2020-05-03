package com.sjtu.project.channelservice.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@Slf4j
public class InputChannel {
    @Id
    String id;

    String processId;

    String targetDataSourceId;

    List<TransformRule> transformRules = new ArrayList<>();

    FusionRule fusionRule;

    String targetServiceId;

    public Mono<Void> onMessage(Message message) {
        log.info("分发来自 {} 的消息 {}", message.getDatasourceId(), message.getContent());
        //TODO 融合
        ObjectNode input = JsonUtil.readTree(message.getContent());
        for (TransformRule transformRule : transformRules) {
            input = transformRule.doTransform(input);
        }
        //TODO 背压控制
        return doDispatch(JsonUtil.writeValueAsString(input));
    }

    public Mono<Void> doDispatch(String content) {
        ServiceManagement serviceManagement = ContextUtil.ctx.getBean(ServiceManagement.class);
        DataSourceClient dataSourceClient = ContextUtil.ctx.getBean(DataSourceClient.class);
        return serviceManagement.call(targetServiceId, content)
                .flatMap(message -> dataSourceClient.sendMessage(targetDataSourceId, message));
    }
}
