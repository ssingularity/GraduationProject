package com.sjtu.project.userservice.controller;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.userservice.dao.PermissionInfoRepository;
import com.sjtu.project.userservice.dao.UserRepository;
import com.sjtu.project.userservice.domain.User;
import com.sjtu.project.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionInfoRepository permissionInfoRepository;

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

    @GetMapping("/user/{username}/datasource")
    public Result<List<String>> getUserDataSource(@PathVariable String username) {
        List<String> res = permissionInfoRepository.findOneByUsername(username).getDataSourceIds();
        return ResultUtil.success(res == null ? new ArrayList<>() : res);
    }

    @GetMapping("/user/self")
    public Result<User> getSelf() {
        return ResultUtil.success(userService.getSelf());
    }
}
