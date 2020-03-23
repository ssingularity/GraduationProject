package com.sjtu.project.common.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IntegerDescriptor.class, name = "Integer"),
        @JsonSubTypes.Type(value = StringDescriptor.class, name = "String"),
        @JsonSubTypes.Type(value = ObjectDescriptor.class, name = "Object")
})
@Data
@NoArgsConstructor
public abstract class Descriptor<T> {
    @NonNull
    String keyName;

    @NonNull
    T defaultValue;

    String description;

    abstract ObjectNode generateDataFromJson(ObjectNode json);
}
