package com.sjtu.project.logservice.adapter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/28 21:50
 */
@RestController
public class LogController {
    @GetMapping("/test")
    public void test() {

    }
}
