package com.sjtu.project.common.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;

@Data
@JsonTypeName(value = "Integer")
public class IntegerDescriptor extends Descriptor<Integer> {

    @Override
    public JsonNode generateJsonNodeFromJson(JsonNode json) {
        ObjectNode res = JsonUtil.createObjectNode();
        JsonNode value = json.get(keyName);
        if (value == null) {
            res.put(keyName, defaultValue);
        }
        else {
            res.put(keyName, value.asInt());
        }
        return res;
    }

    @Override
    public Integer getValueFromJson(JsonNode json) {
        JsonNode value = getTargetJsonNode(json);
        return value == null ? defaultValue : value.asInt();
    }
}
