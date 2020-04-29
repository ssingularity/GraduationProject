package com.sjtu.project.servicemanagement.domain;

import java.util.UUID;

public class ServiceFactory {
    public static Service createService(Service service) {
        service.verifySelf();
        service.setId(UUID.randomUUID().toString());
        return service;
    }
}
