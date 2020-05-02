package com.sjtu.project.datasourceservice.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DataSourceDao extends MongoRepository<DataSource, String>, CustomDataSourceDao{
    List<DataSource> findAllByVisibleIsTrue();

    DataSource findOneById(String id);
}
