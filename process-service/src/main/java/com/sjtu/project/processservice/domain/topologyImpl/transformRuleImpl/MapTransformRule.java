package com.sjtu.project.processservice.domain.topologyImpl.transformRuleImpl;

import com.sjtu.project.processservice.domain.topologyImpl.TransformRule;
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
}
