package com.sjtu.project.coreservice.domain;

import com.sjtu.project.coreservice.domain.fusionRule.FusionRule;
import com.sjtu.project.coreservice.domain.transformRule.TransformRule;

import java.util.List;

public interface ChannelService {
    InputChannel createInputChannel(FusionRule fusionRule, List<TransformRule> transformRules, String targetServiceId);
}
