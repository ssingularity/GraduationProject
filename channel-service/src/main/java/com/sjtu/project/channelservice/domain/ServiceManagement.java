package com.sjtu.project.channelservice.domain;

import com.sjtu.project.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-management")
public interface ServiceManagement {
    @PostMapping("service/{id}/message")
    Result<String> call(@PathVariable(name = "id") String id, @RequestBody String message);
}
