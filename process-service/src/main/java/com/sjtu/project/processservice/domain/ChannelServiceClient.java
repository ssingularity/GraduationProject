package com.sjtu.project.processservice.domain;

import com.sjtu.project.processservice.domain.topologyImpl.FusionRule;
import com.sjtu.project.processservice.domain.topologyImpl.TransformRule;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "channel-service")
public interface ChannelServiceClient {

    InputChannel createInputChannel(FusionRule fusionRule, List<TransformRule> transformRules, String targetServiceId);
}
