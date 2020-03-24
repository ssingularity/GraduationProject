package com.sjtu.project.channelservice.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-management")
public interface ServiceManagement {
    @PostMapping("service/{id}/message")
    void call(@PathVariable(name = "id") String id, @RequestBody String message);
}
