package com.sjtu.project.processservice.dto;

import com.sjtu.project.processservice.domain.topologyImpl.fusionRule.FusionRule;
import com.sjtu.project.processservice.domain.topologyImpl.TransformRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputChannelDTO {
    String id;

    String processId;

    String targetDataSourceId;

    List<TransformRule> transformRules = new ArrayList<>();

    FusionRule fusionRule;

    String targetServiceId;
}
