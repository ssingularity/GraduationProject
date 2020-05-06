package com.sjtu.project.channelservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/6 23:31
 */
@Component
public class RedisLock {
    @Autowired
    StringRedisTemplate redisTemplate;

    public void lock(String key) throws InterruptedException {
        while (true) {
            if (redisTemplate.boundValueOps(key).setIfAbsent("", Duration.ofMinutes(1))) {
                break;
            }
            else {
                Thread.sleep(100);
            }
        }
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}
