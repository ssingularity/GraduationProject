package com.sjtu.project.channelservice.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@Slf4j
public class InputChannel {
    @Id
    String id;

    List<TransformRule> transformRules = new ArrayList<>();

    FusionRule fusionRule;

    String targetServiceId;

    public void onMessage(Message message) {
        log.info("分发来自 {} 的消息 {}", message.getDatasourceId(), message.getContent());
        //TODO 融合
        ObjectNode input = JsonUtil.readTree(message.getContent());
        for (TransformRule transformRule : transformRules) {
            input = transformRule.doTransform(input);
        }
        Constants.ctx.getBean(ServiceManagement.class).call(targetServiceId, JsonUtil.writeValueAsString(input));
    }
}
