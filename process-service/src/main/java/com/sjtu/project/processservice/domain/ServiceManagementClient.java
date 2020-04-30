package com.sjtu.project.processservice.domain;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.processservice.dto.DataSourceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-management")
public interface ServiceManagementClient {
    @PostMapping("/service/{id}/datasource")
    Result<DataSourceDTO> createDataSource(@PathVariable(name = "id") String serviceId);
}
