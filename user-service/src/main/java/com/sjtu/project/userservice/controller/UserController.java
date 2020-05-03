package com.sjtu.project.userservice.controller;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.userservice.dao.UserRepository;
import com.sjtu.project.userservice.domain.User;
import com.sjtu.project.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    public Result<List<User>> getUsers() {
        return ResultUtil.success(userRepository.findAll());
    }

    @PostMapping("/user")
    public Result<User> addUser(@RequestBody User user) {
        return ResultUtil.success(userService.addUser(user));
    }

    @PutMapping("/user")
    public Result<User> modifyUser(@RequestBody User user){
        return ResultUtil.success(userService.modify(user));
    }

    @GetMapping("/user/{username}")
    public Result<User> getUser(@PathVariable String username) {
        return ResultUtil.success(userService.findOne(username));
    }

    @GetMapping("/user/self")
    public Result<User> getSelf() {
        return ResultUtil.success(userService.getSelf());
    }
}
