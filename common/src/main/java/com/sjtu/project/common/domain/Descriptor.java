package com.sjtu.project.common.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thinkpad
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@Data
@NoArgsConstructor
public abstract class Descriptor<T> {
    String keyName;

    T defaultValue;

    String description;

    /**
     * 输入Json格式的JsonNode获得符合Descriptor描述的Json
     *
     * @param json
     * @return JsonNode
     */
    public abstract JsonNode generateJsonNodeFromJson(JsonNode json);

    /**
     * 输入Json格式的JsonNode获得符合Descriptor描述的对应的值
     *
     * @param json
     * @return T
     */
    public abstract T getValueFromJson(JsonNode json);

    protected JsonNode getTargetJsonNode(JsonNode jsonNode) {
        return jsonNode.get(keyName);
    }
}
