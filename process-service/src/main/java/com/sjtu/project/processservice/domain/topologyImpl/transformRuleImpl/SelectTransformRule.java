package com.sjtu.project.processservice.domain.topologyImpl.transformRuleImpl;


import com.sjtu.project.processservice.domain.topologyImpl.TransformRule;
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
}
