package com.sjtu.project.userservice.dao;

import com.sjtu.project.userservice.domain.PermissionInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionInfoRepository extends MongoRepository<PermissionInfo, String> {
    PermissionInfo findOneByUserId(String userId);
}
