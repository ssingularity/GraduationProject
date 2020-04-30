package com.sjtu.project.logservice.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.logservice.domain.Log;
import com.sjtu.project.logservice.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/28 21:50
 */
@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @PostMapping(value = "/log")
    public Result<Log> createLog(@RequestBody Log log) {
        return ResultUtil.success(logService.createLog(log));
    }

    @PostMapping(value = "/logs")
    public Result<List<Log>> createLogs(@RequestBody List<Log> logs) {
        return ResultUtil.success(logService.createLogs(logs));
    }
}
