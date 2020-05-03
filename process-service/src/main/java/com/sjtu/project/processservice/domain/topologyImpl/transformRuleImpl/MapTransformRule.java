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
public class MapTransformRule implements TransformRule {
    List<PathPair> mapRule = new ArrayList<>();
}
