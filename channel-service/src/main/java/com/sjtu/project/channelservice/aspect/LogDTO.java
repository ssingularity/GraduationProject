package com.sjtu.project.channelservice.aspect;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author loumoon
 * @date 2020-04-29 17:45
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LogDTO {
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date timestamp;

    private String processId;

    private String content;

    private String dataSourceId;

    private String dataSourceName;

    private String serviceId;

    private String serviceName;

    private Long executingQueueSize;

    private Long waitingQueueSize;
}
