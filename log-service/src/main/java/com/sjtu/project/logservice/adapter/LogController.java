package com.sjtu.project.logservice.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.logservice.dao.LogRepository;
import com.sjtu.project.logservice.domain.Log;
import com.sjtu.project.logservice.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/28 21:50
 */
@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @Autowired
    private LogRepository repo;

    @PostMapping(value = "/log")
    public Result<String> createLog(@RequestBody Log log) {
        new Thread(() -> logService.createLog(log)).start();
        return ResultUtil.success();
    }

    @GetMapping(value = "/log")
    public Result<List<Log>>  getLogs(@RequestParam(name = "processId") String processId) {
        return ResultUtil.success(repo.findAllByProcessId(processId));
    }

    @PostMapping(value = "/logs")
    public Result<List<Log>> createLogs(@RequestBody List<Log> logs) {
        return ResultUtil.success(logService.createLogs(logs));
    }
}
