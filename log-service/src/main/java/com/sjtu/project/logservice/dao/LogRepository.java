package com.sjtu.project.logservice.dao;


import com.sjtu.project.logservice.domain.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
    Log findOneById(String id);
}
