package com.sjtu.project.processservice.domain;

import com.sjtu.project.processservice.domain.topologyImpl.FusionRule;
import com.sjtu.project.processservice.domain.topologyImpl.TransformRule;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InputChannel {
    String id;

    String processId;

    String targetDataSourceId;

    List<TransformRule> transformRules = new ArrayList<>();

    FusionRule fusionRule;

    String targetServiceId;
}
