package com.sjtu.project.datasourceservice;

import com.sjtu.project.common.util.JsonUtil;
import com.sjtu.project.datasourceservice.domain.Constants;
import com.sjtu.project.datasourceservice.domain.DataSource;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

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
        Assert.assertNotNull(JsonUtil.readVaules(redisTemplate.opsForValue().get("test"), DataSource.class));
    }

    @Test
    public void testKafka() {
        KafkaProducer kafkaProducer = initProducer();
        ProducerRecord<String, String> record = new ProducerRecord<>("whatever", "test");
        kafkaProducer.send(record, (x, y) -> {
            KafkaConsumer<String, String> consumer = initConsumer();
            ConsumerRecords<String, String> res = consumer.poll(Duration.ofMillis(200));
            Assert.assertEquals(1, res.count()) ;
            Assert.assertEquals("test", res.iterator().next().value());
        });
    }

    private KafkaConsumer initConsumer() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.KAFKA_URL);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        KafkaConsumer kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Collections.singleton("whatever"));
        return kafkaConsumer;
    }

    private KafkaProducer initProducer() {
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.KAFKA_URL);
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer kafkaProducer = new KafkaProducer<>(p);
        return kafkaProducer;
    }
}