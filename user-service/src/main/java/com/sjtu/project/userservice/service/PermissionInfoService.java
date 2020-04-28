package com.sjtu.project.userservice.service;

import com.sjtu.project.userservice.dao.PermissionInfoRepository;
import com.sjtu.project.userservice.domain.PermissionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionInfoService {
    @Autowired
    PermissionInfoRepository repository;

    public void addDataSourcePermission(String userId, String resourceId) {
        PermissionInfo info = repository.findOneByUserId(userId);
        info.addDataSourceId(resourceId);
        repository.save(info);
    }
}
