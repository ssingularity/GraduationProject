package com.sjtu.project.servicemanagement.domain;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.servicemanagement.dto.DataSourceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/29 17:20
 */
@FeignClient(name = "datasource-service")
public interface DataSourceClient {
    @PostMapping("/datasource")
    Result<DataSourceDTO> createDataSource(@RequestBody DataSource dataSource);
}
