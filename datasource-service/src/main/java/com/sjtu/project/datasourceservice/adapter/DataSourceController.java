package com.sjtu.project.datasourceservice.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.datasourceservice.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataSourceController {
    @Autowired
    DataSourceDao dataSourceDao;

    @Autowired
    DataSourceService dataSourceService;

    @Autowired
    AclService aclService;

    @GetMapping("/datasource")
    public Result<List<DataSource>> getAll() {
        return ResultUtil.success(dataSourceDao.findAllByVisibleIsTrue());
    }

    @PostMapping("/datasource")
    public Result<DataSource> addDataSource(@RequestBody DataSource dataSource) {
        return ResultUtil.success(dataSourceDao.save(dataSourceService.create(dataSource)));
    }

    @PostMapping("/datasource/{id}/channel")
    public Result<String> registerChannel(@PathVariable(name = "id") String id, @RequestBody InputChannel channel) {
        DataSource ds = dataSourceDao.queryById(id);
        ds.registerChannel(channel);
        return ResultUtil.success();
    }

    @PostMapping("/datasource/{dsId}/user/{username}")
    public Result<String> authorize(@PathVariable(name = "dsId") String dsId,
                                    @PathVariable(name = "username") String username) {
        DataSource ds = dataSourceDao.queryById(dsId);
        aclService.authorize(username, ds);
        return ResultUtil.success();
    }
}
