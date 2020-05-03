package com.sjtu.project.channelservice.clientImpl;

import com.sjtu.project.channelservice.domain.ServiceManagement;
import com.sjtu.project.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/3 19:41
 */
@Service
@Slf4j
public class ServiceManagementImpl implements ServiceManagement {
    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public Mono<String> call(String id, String message) {
        return webClientBuilder.baseUrl("http://service-management/").build()
                .post()
                .uri("/service/" + id + "/message")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(message)
                .retrieve()
                .bodyToMono(Result.class)
                .map(Result::getData)
                .map(Object::toString);
    }
}
