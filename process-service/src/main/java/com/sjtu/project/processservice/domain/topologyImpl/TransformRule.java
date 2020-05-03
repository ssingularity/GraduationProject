package com.sjtu.project.processservice.domain.topologyImpl;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sjtu.project.processservice.domain.topologyImpl.transformRuleImpl.MapTransformRule;
import com.sjtu.project.processservice.domain.topologyImpl.transformRuleImpl.SelectTransformRule;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MapTransformRule.class, name = "map"),
        @JsonSubTypes.Type(value = SelectTransformRule.class, name = "select"),
})
public interface TransformRule {
}
