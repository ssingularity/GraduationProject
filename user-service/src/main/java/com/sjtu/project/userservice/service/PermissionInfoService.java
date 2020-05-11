package com.sjtu.project.userservice.service;

import com.sjtu.project.userservice.dao.PermissionInfoRepository;
import com.sjtu.project.userservice.domain.PermissionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PermissionInfoService {
    @Autowired
    PermissionInfoRepository repository;

    public void addDataSourcePermission(String username, String resourceId) {
        PermissionInfo info = repository.findOneByUsername(username);
        if (info == null) {
            info = PermissionInfo.builder()
                    .username(username)
                    .dataSourceIds(new ArrayList<>())
                    .build();
        }
        info.addDataSourceId(resourceId);
        repository.save(info);
    }
}
