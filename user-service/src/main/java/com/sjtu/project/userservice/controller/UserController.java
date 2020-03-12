package com.sjtu.project.userservice.controller;

import com.sjtu.project.common.response.PageResult;
import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.userservice.domain.User;
import com.sjtu.project.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public Result<PageResult<User>> getUsers(@RequestParam(name = "pageNum", defaultValue = "1000") Integer pageNum,
                                             @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex) {
        return ResultUtil.success(userService.findAll(PageRequest.of(pageIndex, pageNum)));
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
