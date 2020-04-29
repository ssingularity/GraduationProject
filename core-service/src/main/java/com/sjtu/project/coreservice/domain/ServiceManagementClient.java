package com.sjtu.project.coreservice.domain;

import com.sjtu.project.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-management")
public interface ServiceManagementClient {
    @GetMapping("/service/{id}/datasource")
    Result<DataSource> getDataSourceOf(@PathVariable(name = "id") String id);
}
