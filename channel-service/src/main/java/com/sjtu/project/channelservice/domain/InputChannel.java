package com.sjtu.project.channelservice.domain;

import com.sjtu.project.common.domain.Message;
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
    }
}
