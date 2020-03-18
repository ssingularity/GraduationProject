package com.sjtu.project.datasourceservice.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataSourceDao extends MongoRepository<DataSource, String> {
}
