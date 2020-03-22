package com.sjtu.project.channelservice.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InputChannelDao extends MongoRepository<InputChannel, String>, CustomInputChannelDao {
}
