package com.sjtu.project.channelservice.daoImpl;

import com.sjtu.project.channelservice.domain.CustomInputChannelDao;
import com.sjtu.project.channelservice.domain.InputChannel;
import com.sjtu.project.common.exception.ObjectNotFoundException;
import com.sjtu.project.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class InputChannelDaoImpl implements CustomInputChannelDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public InputChannel queryOneById(String id) {
        String json = redisTemplate.boundValueOps(generateRedisKey(id)).get();
        if (json == null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            InputChannel inputChannel = mongoTemplate.findOne(query, InputChannel.class);
            if (inputChannel == null) {
                throw new ObjectNotFoundException("InputChannel");
            }
            else {
                redisTemplate.boundValueOps(generateRedisKey(id)).set(JsonUtil.writeValueAsString(inputChannel), Duration.ofMinutes(10));
                return inputChannel;
            }
        }
        else {
            return JsonUtil.readValues(json, InputChannel.class);
        }
    }

    private String generateRedisKey(String id) {
        return "Channel:" + id;
    }
}
