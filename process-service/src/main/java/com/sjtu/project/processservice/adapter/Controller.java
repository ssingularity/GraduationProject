package com.sjtu.project.processservice.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.common.util.UserUtil;
import com.sjtu.project.processservice.dao.ProcessDao;
import com.sjtu.project.processservice.domain.Process;
import com.sjtu.project.processservice.domain.ProcessStatus;
import com.sjtu.project.processservice.dto.ProcessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/29 18:01
 */
@RestController
public class Controller {
    @Autowired
    ProcessDao dao;

    @PostMapping("/process")
    public Result<Process> createProcess(@RequestBody Process process) {
        process.setOwner(UserUtil.getUsername());
        process.setStatus(ProcessStatus.STOPPED);
        return ResultUtil.success(dao.save(process));
    }

    @GetMapping("/process/mine")
    public Result<List<ProcessDTO>> getMyProcess() {
        return ResultUtil.success(dao.findAllByOwner(UserUtil.getUsername()));

    }

    @GetMapping("/process/{id}")
    public Result<Process> getById(@PathVariable(name = "id") String id) {
        return ResultUtil.success(dao.findOneById(id));
    }

    @PostMapping("/process/{id}/start")
    public Result<String> startProcess(@PathVariable(name = "id") String id) {
        Process process = dao.findOneById(id);
        process.start();
        dao.save(process);
        return ResultUtil.success();
    }

    @PostMapping("/process/{id}/stop")
    public Result<String> stopProcess(@PathVariable(name = "id") String id) {
        Process process = dao.findOneById(id);
        process.stop();
        dao.save(process);
        return ResultUtil.success();
    }
}
