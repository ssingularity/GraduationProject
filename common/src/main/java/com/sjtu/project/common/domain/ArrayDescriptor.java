package com.sjtu.project.common.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;

import java.util.List;

/**
 * @author loumoon
 * @date 2020-04-11 14:35
 */
@Data
@JsonTypeName(value = "Array")
public class ArrayDescriptor extends Descriptor<List> {
    Descriptor object;

    @Override
    public JsonNode generateJsonNodeFromJson(JsonNode jsonNode) {
        // TODO
        if (keyName == null) {
            ArrayNode res = JsonUtil.createArrayNode();
            return res;
        } else {
            ObjectNode res = JsonUtil.createObjectNode();
            return res;
        }
    }

    @Override
    public List getValueFromJson(JsonNode json) {
        return null;
    }
}
