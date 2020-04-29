package com.sjtu.project.coreservice.domain;

import com.sjtu.project.coreservice.domain.topologyImpl.FusionRule;
import com.sjtu.project.coreservice.domain.topologyImpl.TransformRule;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InputChannel {
    String id;

    String processId;

    List<TransformRule> transformRules = new ArrayList<>();

    FusionRule fusionRule;

    String targetServiceId;
}
