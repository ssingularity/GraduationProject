package com.sjtu.project.userservice.service;

import com.sjtu.project.userservice.domain.User;

public interface UserService {
    User addUser(User user);

    User findOne(String username);

    User getSelf();

    User findById(String id);

    User modify(User user);
}
