package com.sjtu.project.channelservice.domain;

import reactor.core.publisher.Mono;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/30 0:01
 */
public interface DataSourceClient {
    Mono<Void> sendMessage(String datasourceId, String message);
}
