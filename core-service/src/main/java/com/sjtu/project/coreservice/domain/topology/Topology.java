package com.sjtu.project.coreservice.domain.topology;

import com.sjtu.project.coreservice.domain.FusionRule;
import com.sjtu.project.coreservice.domain.TransformRule;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public abstract class Topology {
    Set<Topology> inputList = new HashSet<>();

    String id;

    String name;

    FusionRule fusionRule;

    List<TransformRule> transformRuleList = new ArrayList<>();

    abstract void addInput(Topology topology);

    boolean isAccessorOf(Topology topology) {
        return getInputList().contains(topology);
    }
}
