package com.sjtu.project.common.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ObjectDescriptor extends Descriptor<Object> {
    List<Descriptor> children = new ArrayList<>();

    @Override
    ObjectNode generateDataFromJson(ObjectNode json) {
        ObjectNode res = JsonUtil.createObjectNode();
        final ObjectNode objectNode = (ObjectNode)json.get(keyName);
        List tmp = children.stream()
                .map(x -> x.generateDataFromJson(objectNode))
                .collect(Collectors.toList());
        res.set(keyName, JsonUtil.contact(tmp));
        return res;
    }
}
