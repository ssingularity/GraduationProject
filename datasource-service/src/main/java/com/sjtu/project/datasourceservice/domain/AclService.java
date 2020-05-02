package com.sjtu.project.datasourceservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/26 20:30
 */
@Service
@Slf4j
public class AclService {
    private static String PRINCIPAL_PREFIX = "User:";

    private static String WILDCARD = "*";

    @Autowired
    Admin adminClient;

    public void authorize(String username, DataSource ds) {
        log.info("用户 {} 完成了对于Topic {} 的授权", username, ds.topic);
        ResourcePattern resourcePattern = new ResourcePattern(ResourceType.TOPIC, ds.getTopic(), PatternType.LITERAL);
        AccessControlEntry accessControlEntry = new AccessControlEntry(PRINCIPAL_PREFIX + username, WILDCARD, AclOperation.ALL, AclPermissionType.ALLOW);
        AclBinding aclBinding = new AclBinding(resourcePattern, accessControlEntry);
        adminClient.createAcls(Arrays.asList(aclBinding));
    }
}
