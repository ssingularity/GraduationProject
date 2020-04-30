package com.sjtu.project.logservice.domain;


import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonTypeName(value = "Service")
public class ServiceLog extends Log{
    @NotBlank
    private String serviceId;

    @NotBlank
    private String apiId;

    @Builder
    public ServiceLog(String id, Date timestamp, String serviceId, String apiId, String processId, String content){
        super.id = id;
        super.timestamp = timestamp;
        this.serviceId = serviceId;
        this.apiId = apiId;
        super.processId = processId;
        super.content = content;
    }
}
