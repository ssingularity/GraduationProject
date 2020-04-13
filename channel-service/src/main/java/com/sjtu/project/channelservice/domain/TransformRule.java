package com.sjtu.project.channelservice.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author thinkpad
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
public interface TransformRule {
    ObjectNode doTransform(ObjectNode input);
}
