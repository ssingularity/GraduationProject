package com.sjtu.project.servicemanagement.domain;

public interface CustomServiceDao {
    Service queryById(String id);

    Service store(Service service);
}
