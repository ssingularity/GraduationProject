package com.sjtu.project.userservice.service;

import com.sjtu.project.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/26 20:51
 */
@FeignClient(name = "datasource-service")
public interface DataSourceClient {
    @PostMapping("/datasource/{dsId}/user/{userId}")
    Result<String> authorize(@PathVariable(name = "dsId") String dsId, @PathVariable(name = "userId") String userId);
}
