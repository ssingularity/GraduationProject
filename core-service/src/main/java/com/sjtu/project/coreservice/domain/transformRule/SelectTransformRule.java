package com.sjtu.project.coreservice.domain.transformRule;


import com.fasterxml.jackson.annotation.JsonTypeName;
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
@JsonTypeName(value = "select")
public class SelectTransformRule implements TransformRule {
    List<Path> expectedKeys = new ArrayList<>();
}
