package com.sjtu.project.channelservice.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/3 19:23
 */
public interface ReactiveChannelDao extends ReactiveMongoRepository<InputChannel, String> {
}
