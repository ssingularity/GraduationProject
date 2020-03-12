package com.sjtu.project.userservice.service;

import com.sjtu.project.common.response.PageResult;
import com.sjtu.project.userservice.domain.User;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    PageResult<User> findAll(PageRequest pageRequest);

    User addUser(User user);

    User findOne(String username);

    User getSelf();

    User findById(String id);

    User modify(User user);
}
