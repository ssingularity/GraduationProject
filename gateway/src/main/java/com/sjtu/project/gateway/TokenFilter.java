package com.sjtu.project.gateway;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/8 20:57
 */
@Component
@Slf4j
public class TokenFilter implements GlobalFilter, Ordered {
    final static private String LOGIN_PATH = "/user-service/login";

    final static private String REGISTER_PATH = "/user-service/user";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (noFilterNeed(request)) {
            return chain.filter(exchange);
        }
        else {
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isEmpty(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            else {
                return chain.filter(exchange);
            }
        }
    }

    private boolean noFilterNeed(ServerHttpRequest request) {
        String path = request.getPath().toString();
        return path.startsWith(LOGIN_PATH) ||
                (path.startsWith(REGISTER_PATH) && request.getMethod() == HttpMethod.POST);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
