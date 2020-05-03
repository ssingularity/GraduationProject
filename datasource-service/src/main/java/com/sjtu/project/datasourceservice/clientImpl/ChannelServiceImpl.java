package com.sjtu.project.datasourceservice.clientImpl;

import com.sjtu.project.common.domain.Message;
import com.sjtu.project.datasourceservice.domain.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/3 21:13
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> dispatchMessage(String channelId, Message message) {
        return webClientBuilder.baseUrl("http://channel-service/").build()
                .post()
                .uri("/inputchannel/" + channelId + "/message")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(message)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
