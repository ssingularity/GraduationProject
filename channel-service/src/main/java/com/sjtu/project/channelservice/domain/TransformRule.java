package com.sjtu.project.channelservice.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.channelservice.transformImpl.MapTransformRule;
import com.sjtu.project.channelservice.transformImpl.SelectTransformRule;

/**
 * @author thinkpad
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MapTransformRule.class, name = "map"),
        @JsonSubTypes.Type(value = SelectTransformRule.class, name = "select"),
})
public interface TransformRule {
    ObjectNode doTransform(ObjectNode input);
}
