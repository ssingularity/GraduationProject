package com.sjtu.project.channelservice.transformImpl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.channelservice.domain.TransformRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thinkpad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapTransformRule implements TransformRule {
    PathPair mapRule;

    @Override
    public ObjectNode doTransform(ObjectNode input) {
        String value = mapRule.getSource().getValueFromJson(input);
        mapRule.getSource().deleteFromJson(input);
        mapRule.getTarget().setValueToJson(input, value);
        return input;
    }
}
