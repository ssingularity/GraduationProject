package com.sjtu.project.datasourceservice.domain;

import com.sjtu.project.common.domain.Descriptor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class DataSource {
    @Id
    String id;

    String name;

    String topic;

    String description;

    List<Descriptor> dataDescriptor;
}
