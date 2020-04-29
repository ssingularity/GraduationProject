package com.sjtu.project.datasourceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sjtu.project.common.domain.Descriptor;
import com.sjtu.project.common.util.ContextUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

@Data
@Document
public class DataSource {
    @Id
    String id;

    String name;

    String topic;

    String description;

    boolean visible = true;

    Descriptor schema;

    public void verifySelf() {
        //TODO 检验参数可靠性
    }

    public void registerChannel(InputChannel inputChannel) {
        StringRedisTemplate redisTemplate = ContextUtil.ctx.getBean(StringRedisTemplate.class);
        redisTemplate.boundSetOps(generateRedisKey()).add(inputChannel.id);
    }

    @JsonIgnore
    public Set<String> registeredChannels() {
        StringRedisTemplate redisTemplate = ContextUtil.ctx.getBean(StringRedisTemplate.class);
        return redisTemplate.boundSetOps(generateRedisKey()).members();
    }

    private String generateRedisKey() {
        return "DataSource:" + id;
    }

    public void dispatchMessage(DataSourceListener listener, List<String> values) {
        for (String value : values) {
            listener.onMessage(this, value);
        }
    }
}
