package com.sjtu.project.servicemanagement.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/3 17:27
 */
public interface ReactiveServiceDao extends ReactiveMongoRepository<Service, String> {
    Mono<Service> findOneById(String id);
}
