package com.sjtu.project.channelservice.transformImpl;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.channelservice.domain.TransformRule;
import com.sjtu.project.common.util.JsonUtil;
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
public class SelectTransformRule implements TransformRule {
    List<Path> expectedKeys = new ArrayList<>();

    @Override
    public ObjectNode doTransform(ObjectNode input) {
        ObjectNode res = JsonUtil.createObjectNode();
        for (Path keyPath : expectedKeys) {
            String targetValue = keyPath.getValueFromJson(input);
            keyPath.setValueToJson(res, targetValue);
        }
        return res;
    }
}
