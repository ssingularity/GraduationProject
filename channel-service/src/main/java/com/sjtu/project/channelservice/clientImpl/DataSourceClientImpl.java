package com.sjtu.project.channelservice.clientImpl;

import com.sjtu.project.channelservice.domain.DataSourceClient;
import com.sjtu.project.common.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/3 19:40
 */
@Service
public class DataSourceClientImpl implements DataSourceClient {
    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public Mono<Result> sendMessage(String datasourceId, String message) {
        return webClientBuilder.baseUrl("http://datasource-service/").build()
                .post()
                .uri("/datasource/" + datasourceId + "/message")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(message)
                .retrieve()
                .bodyToMono(Result.class);
    }
}
