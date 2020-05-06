package com.sjtu.project.processservice.domain.topologyImpl.fusionRule;

import lombok.Data;

import java.util.Set;

@Data
public class FusionRule {
    String name;

    String description;

    String keyName;

    Set<String> dataSourceIdSet;
}
