package com.sjtu.project.channelservice.aspect;

import com.sjtu.project.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "log-service")
public interface LogServiceClient {

    @PostMapping("/log")
    Result createLog(LogDTO log);

    @PostMapping("/logs")
    Result createLogs(List<LogDTO> logs);
}
