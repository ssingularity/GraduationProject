package com.sjtu.project.datasourceservice.domain;

import com.sjtu.project.common.domain.Descriptor;
import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Data
@Document
public class DataSource {
    @Id
    String id;

    String name;

    String topic;

    String description;

    boolean visible = true;

    List<Descriptor> dataDescriptor;

    public void verifySelf() {
        //TODO 检验参数可靠性
    }

    public void registerChannel(InputChannel inputChannel) {
        StringRedisTemplate redisTemplate = Constants.ctx.getBean(StringRedisTemplate.class);
        redisTemplate.boundSetOps(id).add(inputChannel.id);
    }

    public void startWithListener(DataSourceListener listener) {
        new Thread(() -> {
            Properties consumerProperties = new Properties();
            consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.KAFKA_URL);
            consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, name);
            KafkaConsumer kafkaConsumer = new KafkaConsumer<>(consumerProperties);
            kafkaConsumer.subscribe(Collections.singleton(topic));
            while(true) {
                ConsumerRecords<String, String> res = kafkaConsumer.poll(Duration.ofMillis(200));
                for (ConsumerRecord<String, String> record : res) {
                    listener.onMessage(id, record.value());
                }
            }
        }).start();
    }
}
