package com.sjtu.project.coreservice.domain.transformRule;

import lombok.Data;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/13 17:45
 */
@Data
public class Path {
    final private static String SPLITTER = "\\.";

    private String targetKey;
}
