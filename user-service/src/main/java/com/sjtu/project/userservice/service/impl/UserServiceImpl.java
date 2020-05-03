package com.sjtu.project.userservice.service.impl;

import com.sjtu.project.common.exception.ObjectNotFoundException;
import com.sjtu.project.userservice.util.UserUtil;
import com.sjtu.project.userservice.dao.UserRepository;
import com.sjtu.project.userservice.domain.User;
import com.sjtu.project.userservice.domain.UserAuthority;
import com.sjtu.project.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public User modify(User user) {
        return userRepository.save(user);
    }

    @Override
    public User addUser(User user) {
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Arrays.asList(new UserAuthority()));
        return userRepository.save(user);
    }

    @Override
    public User findOne(String username) {
        User res = userRepository.findOneByUsername(username).orElse(null);
        if (res == null) {
            throw new ObjectNotFoundException("user");
        }
        return res;
    }

    @Override
    public User findById(String id) {
        User res = userRepository.findById(id).orElse(null);
        if (res == null) {
            throw new ObjectNotFoundException("user");
        }
        return res;
    }

    @Override
    public User getSelf() {
        String username = UserUtil.getUsername();
        return findOne(username);
    }
}
