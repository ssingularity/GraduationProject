package com.sjtu.project.coreservice.domain.transformRule;

import com.fasterxml.jackson.annotation.JsonTypeName;
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
}
