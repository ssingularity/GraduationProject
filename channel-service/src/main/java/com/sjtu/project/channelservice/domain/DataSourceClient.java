package com.sjtu.project.channelservice.domain;

import com.sjtu.project.common.response.Result;
import reactor.core.publisher.Mono;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/30 0:01
 */
public interface DataSourceClient {
    Mono<Result> sendMessage(String datasourceId, String message);
}
