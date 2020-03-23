package com.sjtu.project.common.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RootDescriptor {
    List<Descriptor> body;

    public String generateOutputFromJson(String json) {
        final ObjectNode objectNode = JsonUtil.readTree(json);
        List tmp = body.stream()
                .map(x -> x.generateDataFromJson(objectNode))
                .collect(Collectors.toList());
        ObjectNode resNode = JsonUtil.contact(tmp);
        return JsonUtil.writeValueAsString(resNode);
    }
}
