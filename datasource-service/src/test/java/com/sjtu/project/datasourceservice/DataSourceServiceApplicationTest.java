package com.sjtu.project.datasourceservice;

import com.sjtu.project.common.util.JsonUtil;
import com.sjtu.project.datasourceservice.domain.DataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = DataSourceServiceApplication.class)
@RunWith(SpringRunner.class)
public class DataSourceServiceApplicationTest {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate() {
        String value = JsonUtil.writeValueAsString(new DataSource());
        redisTemplate.opsForValue().set("test", value);
        Assert.assertEquals(value, redisTemplate.opsForValue().get("test"));
        Assert.assertNotNull(JsonUtil.readValues(redisTemplate.opsForValue().get("test"), DataSource.class));
    }
}