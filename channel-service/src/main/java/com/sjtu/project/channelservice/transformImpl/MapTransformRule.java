package com.sjtu.project.channelservice.transformImpl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.channelservice.domain.TransformRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author thinkpad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName(value = "map")
public class MapTransformRule implements TransformRule {
    Map<Path, Path> mapRule = new HashMap<>();

    @Override
    public ObjectNode doTransform(ObjectNode input) {
        for (Map.Entry<Path, Path> map : mapRule.entrySet()) {
            String value = map.getKey().getValueFromJson(input);
            map.getKey().deleteFromJson(input);
            map.getValue().setValueToJson(input, value);
        }
        return input;
    }
}
