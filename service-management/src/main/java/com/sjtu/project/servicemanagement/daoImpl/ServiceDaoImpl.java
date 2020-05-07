package com.sjtu.project.servicemanagement.daoImpl;

import com.sjtu.project.common.exception.ObjectNotFoundException;
import com.sjtu.project.common.util.JsonUtil;
import com.sjtu.project.servicemanagement.domain.CustomServiceDao;
import com.sjtu.project.servicemanagement.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class ServiceDaoImpl implements CustomServiceDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public Service queryById(String id) {
        String json = redisTemplate.boundValueOps(generateRedisKey(id)).get();
        if (json == null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            Service res = mongoTemplate.findOne(query, Service.class);
            if (res == null) {
                throw new ObjectNotFoundException("Service对象未找到");
            }
            else {
                redisTemplate.boundValueOps(generateRedisKey(id)).set(JsonUtil.writeValueAsString(res), Duration.ofMinutes(1));
                return res;
            }
        }
        else {
            return JsonUtil.readValues(json, Service.class);
        }
    }

    @Override
    public Service store(Service service) {
        redisTemplate.delete(generateRedisKey(service.getId()));
        return mongoTemplate.save(service);
    }

    private String generateRedisKey(String id) {
        return "Service:" + id;
    }
}
