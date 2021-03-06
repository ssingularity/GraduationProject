package com.sjtu.project.servicemanagement.domain;

import com.sjtu.project.common.domain.Descriptor;
import lombok.Data;

@Data
public class DataSource {
    String id;

    String name;

    String topic;

    Descriptor schema;

    boolean visible;
}
