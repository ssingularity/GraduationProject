package com.sjtu.project.servicemanagement.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceDao extends MongoRepository<Service, String>, CustomServiceDao {
}
