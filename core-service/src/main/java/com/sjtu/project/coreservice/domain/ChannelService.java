package com.sjtu.project.coreservice.domain;

import java.util.List;

public interface ChannelService {
    InputChannel createInputChannel(FusionRule fusionRule, List<TransformRule> transformRules, String targetServiceId);
}
