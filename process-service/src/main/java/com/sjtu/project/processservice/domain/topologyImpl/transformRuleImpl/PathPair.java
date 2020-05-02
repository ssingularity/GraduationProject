package com.sjtu.project.processservice.domain.topologyImpl.transformRuleImpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/2 18:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathPair {
    Path source;

    Path target;
}
