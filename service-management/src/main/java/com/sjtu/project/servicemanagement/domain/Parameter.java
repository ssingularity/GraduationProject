package com.sjtu.project.servicemanagement.domain;

import com.sjtu.project.common.domain.Descriptor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parameter {
    /**
     * 参数位置
     */
    private ParamIn in;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 数据结构
     */
    private Descriptor schema;
}
