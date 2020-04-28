package com.sjtu.project.datasourceservice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataSourceService {
    @Autowired
    KafkaConsumerSingleton kafkaConsumerSingleton;

    @Autowired
    DataSourceDao dataSourceDao;

    @PostConstruct
    public void wakeUpDeadDataSource() {
        dataSourceDao.findAll().forEach(kafkaConsumerSingleton::subscribe);
    }

    public DataSource create(DataSource ds) {
        ds.verifySelf();
        dataSourceDao.save(ds);
        kafkaConsumerSingleton.subscribe(ds);
        return ds;
    }
}
