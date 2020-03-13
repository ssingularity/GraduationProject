package com.sjtu.project.coreservice.domain.topology;

import com.sjtu.project.coreservice.domain.FusionRule;
import com.sjtu.project.coreservice.domain.TransformRule;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Topology {
    Set<Topology> inputList = new HashSet<>();

    Type type;

    String id;

    String name;

    FusionRule fusionRule;

    List<TransformRule> transformRuleList = new ArrayList<>();

    void addInput(Topology topology) {
        if (type == Type.DataSource) {
            throw new IllegalArgumentException("DataSource为叶节点不能再有Input");
        }
        else {
            inputList.add(topology);
        }
    }

    boolean isAccessorOf(Topology topology) {
        return getInputList().contains(topology);
    }
}
