package com.sjtu.project.logservice.domain;


import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;


@NoArgsConstructor
@Data
@JsonTypeName(value = "DataSource")
public class DataSourceLog extends Log{

    @NotBlank
    private String datasourceId;

    @Builder
    public DataSourceLog(String id, Date timestamp, String datasourceId, String processId, String content){
        super.id = id;
        super.timestamp = timestamp;
        this.datasourceId = datasourceId;
        super.processId = processId;
        super.content = content;
    }
}