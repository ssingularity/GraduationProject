package com.sjtu.project.common.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;

@Data
public class IntegerDescriptor extends Descriptor<Integer> {

    @Override
    ObjectNode generateDataFromJson(ObjectNode json) {
        ObjectNode res = JsonUtil.createObjectNode();
        JsonNode value = json.get(keyName);
        if (value == null) {
            res.put(keyName, defaultValue);
        }
        else {
            res.put(keyName, value.asText());
        }
        return res;
    }
}
