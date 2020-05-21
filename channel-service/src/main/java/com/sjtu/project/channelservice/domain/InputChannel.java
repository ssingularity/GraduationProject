package com.sjtu.project.channelservice.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.common.util.JsonUtil;
import com.sjtu.project.common.util.ResultUtil;
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

    String targetServiceName;

    Integer threshold = Integer.MAX_VALUE;

    public Mono<Result> onMessage(Message message) {
        String input = doFusion(message);
        if (input == null) {
            return Mono.just(ResultUtil.success());
        } else {
            input = doTransform(input);
            BackPressureService backPressureService = ContextUtil.ctx.getBean(BackPressureService.class);
            return backPressureService.dispatchContentForChannel(this, input);
        }
    }

    private String doFusion(Message message) {
        if (fusionRule == null) {
            return message.getContent();
        } else {
            return fusionRule.doFusion(id, message);
        }
    }

    private String doTransform(String content) {
        ObjectNode input = JsonUtil.readTree(content);
        for (TransformRule transformRule : transformRules) {
            input = transformRule.doTransform(input);
        }
        return JsonUtil.writeValueAsString(input);
    }

    public Mono<String> doDispatch(String content) {
        ServiceManagement serviceManagement = ContextUtil.ctx.getBean(ServiceManagement.class);
        return serviceManagement.call(targetServiceId, content);
    }

    public Mono<Result> doDispatch2DataSource(String content) {
        DataSourceClient dataSourceClient = ContextUtil.ctx.getBean(DataSourceClient.class);
        return dataSourceClient.sendMessage(targetDataSourceId, content);
    }
}
