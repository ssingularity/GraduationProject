package com.sjtu.project.channelservice.domain;

import reactor.core.publisher.Mono;

public interface ServiceManagement {
    Mono<String> call(String id, String message);
}
