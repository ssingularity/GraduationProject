package com.sjtu.project.processservice.domain.topologyImpl.fusionRule;

import lombok.Data;

import java.util.List;

@Data
public class FusionRule {
    String name;

    String description;

    String keyName;

    List<String> dataSourceIdSet;
}
