package com.sjtu.project.datasourceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.sjtu.project.common.domain.Descriptor;
import com.sjtu.project.common.exception.ServiceException;
import com.sjtu.project.common.response.ResultCode;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.common.util.JsonUtil;
import com.sjtu.project.datasourceservice.dto.InputChannelDTO;
import lombok.Data;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.StringRedisTemplate;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Document
public class DataSource {
    @Id
    String id;

    String name;

    @NotBlank
    String topic;

    String owner;

    String description;

    boolean visible = true;

    @NotNull
    Descriptor schema;

    public void verifySelf() {
        if (ContextUtil.ctx.getBean(DataSourceDao.class).existsByTopicOrName(topic, name)) {
            throw new ServiceException(ResultCode.DUPLICATED_TOPIC);
        }
    }

    public void registerChannel(InputChannelDTO inputChannelDTO) {
        StringRedisTemplate redisTemplate = ContextUtil.ctx.getBean(StringRedisTemplate.class);
        redisTemplate.boundSetOps(generateRedisKey()).add(inputChannelDTO.getId());
    }

    @JsonIgnore
    public Set<String> registeredChannels() {
        StringRedisTemplate redisTemplate = ContextUtil.ctx.getBean(StringRedisTemplate.class);
        return redisTemplate.boundSetOps(generateRedisKey()).members();
    }

    private String generateRedisKey() {
        return "DataSource:" + id;
    }

    public Flux<Void> dispatchMessage(DataSourceListener listener, List<String> values) {
        return Flux.fromIterable(values).flatMap(value -> listener.onMessage(this, value));
    }

    public void sendMessage(String data) {
        JsonNode node = JsonUtil.readTree(data);
        String sendData = schema.generateJsonNodeFromJson(node).toString();
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, sendData);
        ContextUtil.ctx.getBean(KafkaProducer.class).send(record);
    }

    public void unregisterChannel(String channelId) {
        StringRedisTemplate redisTemplate = ContextUtil.ctx.getBean(StringRedisTemplate.class);
        redisTemplate.boundSetOps(generateRedisKey()).remove(channelId);
    }
}
