package com.sjtu.project.servicemanagement.domain;

import com.sjtu.project.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/29 17:20
 */
@FeignClient(name = "datasource")
public interface DataSourceClient {
    Result<DataSource> createDataSource(@RequestBody DataSource dataSource);
}