package com.sjtu.project.channelservice.serviceImpl;

import com.sjtu.project.channelservice.domain.DataMapStorageService;
import com.sjtu.project.channelservice.util.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/6 12:25
 */
@Service
@Slf4j
public class DataMapStorageServiceImpl implements DataMapStorageService {
    static final private String CHANNEL_PREFIX = "DataSourceListOfFusionRule:";

    static final private String STORE_PREFIX = "StoreForFusionRule:";

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedisLock redisLock;

    @Override
    public void initChannel(String channelId, Set<String> dataSourceIdSet) {
        String channelKey = generateDataSourceLidKey(channelId);
        redisTemplate.boundSetOps(channelKey).add(dataSourceIdSet.toArray(new String[0]));
    }

    @Override
    public void deleteChannel(String channelId) {
        redisTemplate.delete(generateDataSourceLidKey(channelId));
    }

    @Override
    public String getObj(String channelId, String dataSourceId, String key) {
        return redisTemplate.boundValueOps(generateStoreKey(channelId, dataSourceId, key)).get();
    }

    @Override
    public void putObj(String channelId, String dataSourceId, String key, String obj) {
        String storeKey = generateStoreKey(channelId, dataSourceId, key);
        redisTemplate.boundValueOps(storeKey).set(obj, Duration.ofMinutes(10));
    }

    @Override
    public Map<String, String> popCurrentSrcObjMapIfFull(String channelId, String key) {
        String lockKey = generateLockKey(channelId, key);
        try{
            redisLock.lock(lockKey);
            Map<String, String> res = new HashMap<>();
            Set<String> dataSourceIds = redisTemplate.boundSetOps(generateDataSourceLidKey(channelId)).members();
            for (String dataSourceId: dataSourceIds) {
                String tmp = redisTemplate.boundValueOps(generateStoreKey(channelId, dataSourceId, key)).get();
                if (StringUtils.isEmpty(tmp)) {
                    return null;
                }
                else {
                    res.put(dataSourceId, tmp);
                }
            }
            for (String dataSourceId: dataSourceIds) {
                redisTemplate.delete(generateStoreKey(channelId, dataSourceId, key));
            }
            return res;
        }
        catch (Exception e) {
            log.error("", e);
            return null;
        }
        finally {
            redisLock.unlock(lockKey);
        }
    }

    private String generateLockKey(String channelId, String key) {
        return channelId + "-" + key;
    }

    private String generateDataSourceLidKey(String channelId) {
        return CHANNEL_PREFIX + channelId;
    }

    private String generateStoreKey(String channelId, String dataSourceId, String key) {
        return STORE_PREFIX + channelId + "-" + dataSourceId + "-" +key;
    }
}
