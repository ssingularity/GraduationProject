package com.sjtu.project.channelservice.transformImpl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.channelservice.domain.TransformRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thinkpad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapTransformRule implements TransformRule {
    List<PathPair> mapRule = new ArrayList<>();

    @Override
    public ObjectNode doTransform(ObjectNode input) {
        for (PathPair pathPair : mapRule) {
            String value = pathPair.getSource().getValueFromJson(input);
            pathPair.getSource().deleteFromJson(input);
            pathPair.getTarget().setValueToJson(input, value);
        }
        return input;
    }
}
