package com.sjtu.project.common.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonTypeName(value = "Object")
public class ObjectDescriptor extends Descriptor<Object> {
    List<Descriptor> children = new ArrayList<>();

    @Override
    public JsonNode generateJsonNodeFromJson(JsonNode json) {
        if (StringUtils.isEmpty(keyName)) {
            List<ObjectNode> tmp = children.stream()
                    .map(x -> (ObjectNode) x.generateJsonNodeFromJson(json))
                    .collect(Collectors.toList());
            return JsonUtil.contact(tmp);
        } else {
            final JsonNode target = json.get(keyName);
            ObjectNode res = JsonUtil.createObjectNode();
            List<ObjectNode> objectNodes = children.stream()
                    .map(x -> (ObjectNode) x.generateJsonNodeFromJson(target))
                    .collect(Collectors.toList());
            res.set(keyName, JsonUtil.contact(objectNodes));
            return res;
        }
    }

    @Override
    public Object getValueFromJson(JsonNode json) {
        return null;
    }
}
