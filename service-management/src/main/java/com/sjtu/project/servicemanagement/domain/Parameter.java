package com.sjtu.project.servicemanagement.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parameter {
    private String keyName;
    /**
     * 参数位置
     */
    private ParamIn in;

    /**
     * 是否必填
     */
    private Boolean required;
}
