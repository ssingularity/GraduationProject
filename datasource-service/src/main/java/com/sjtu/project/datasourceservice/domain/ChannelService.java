package com.sjtu.project.datasourceservice.domain;


import com.sjtu.project.common.domain.Message;
import reactor.core.publisher.Mono;

public interface ChannelService {
    Mono<Void> dispatchMessage(String channelId, Message message);
}
