package com.sjtu.project.datasourceservice.domain;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/28 13:28
 */
@Component
public class KafkaConsumerSingleton {
    @Autowired
    private KafkaConsumer<String, String> kafkaConsumer;

    @Autowired
    DataSourceListener dataSourceListener;

    private Set<String> topics = new HashSet<>();

    private Map<String, DataSource> topic2DataSource = new HashMap<>();

    private ExecutorService executor = Executors.newCachedThreadPool();

    private ExecutorService singleExecutor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void startListen() {
        singleExecutor.submit(() -> {
            try {
                while (true) {
                    if (!canPoll()) {
                        Thread.sleep(1000);
                        continue;
                    }
                    ConsumerRecords<String, String> msgList = null;
                    synchronized (kafkaConsumer) {
                        msgList = kafkaConsumer.poll(1000);
                    }
                    if (null != msgList && msgList.count() > 0) {
                        Map<String, List<String>> topic2MessageList = new HashMap<>();
                        for (ConsumerRecord<String, String> record : msgList) {
                            String topic = record.topic();
                            String value = record.value();
                            List<String> messageList = topic2MessageList.getOrDefault(topic, new ArrayList<>());
                            messageList.add(value);
                            topic2MessageList.put(topic, messageList);
                        }
                        for (Map.Entry<String, List<String>> entry : topic2MessageList.entrySet()) {
                            DataSource ds = topic2DataSource.get(entry.getKey());
                            executor.submit(() -> ds.dispatchMessage(dataSourceListener, entry.getValue()));
                        }
                    } else {
                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                kafkaConsumer.close();
            }
        });
    }

    @PreDestroy
    public void destroy() {
        if (kafkaConsumer != null) {
            kafkaConsumer.close();
        }
        if (singleExecutor != null) {
            singleExecutor.shutdownNow();
        }
        if (executor != null) {
            try {
                executor.shutdown();
                if (executor != null && !executor.awaitTermination(60, TimeUnit.MILLISECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
    }

    public void subscribe(DataSource ds) {
        synchronized (kafkaConsumer) {
            topic2DataSource.put(ds.getTopic(), ds);
            this.subscribe(Arrays.asList(ds.getTopic()));
        }
    }

    private void subscribe(List<String> newTopics) {
        boolean refreshed = false;
        for (String t : newTopics) {
            if (!topics.contains(t)) {
                topics.add(t);
                refreshed = true;
            }
        }
        if (refreshed) {
            kafkaConsumer.unsubscribe();
            kafkaConsumer.subscribe(topics);
        }
    }

    synchronized public void unsubscribe(DataSource ds) {
        synchronized (kafkaConsumer) {
            topic2DataSource.remove(ds.getId());
            this.unsubscribe(Arrays.asList(ds.getTopic()));
        }
    }

    private void unsubscribe(List<String> deletedTopics) {
        boolean refreshed = false;
        for (String t : deletedTopics) {
            if (topics.contains(t)) {
                topics.remove(t);
                refreshed = true;
            }
        }
        if (refreshed) {
            kafkaConsumer.unsubscribe();
            kafkaConsumer.subscribe(topics);
        }
    }

    public boolean canPoll() {
        return topics.size() > 0;
    }

}
