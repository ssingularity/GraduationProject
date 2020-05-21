package com.sjtu.project.logservice.domain;

import lombok.Data;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/21 15:25
 */
@Data
public class ThresholdLog extends Log{
    Long workLoad;
}
