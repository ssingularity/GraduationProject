package com.sjtu.project.processservice.domain;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.processservice.dto.InputChannelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "datasource-service")
public interface DataSourceServiceClient {
    @PostMapping("/datasource/{id}/channel")
    Result<String> register(@PathVariable(name = "id") String id, @RequestBody InputChannelDTO channel);
}