package com.sjtu.project.channelservice.domain;

import com.sjtu.project.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/30 0:01
 */
@FeignClient(name = "datasource-service")
public interface DataSourceClient {
    @PostMapping("/datasource/{id}/message")
    Result<String> sendMessage(@PathVariable("id") String datasourceId, @RequestBody String message);
}
