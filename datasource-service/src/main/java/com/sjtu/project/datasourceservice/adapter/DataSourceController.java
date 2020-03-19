package com.sjtu.project.datasourceservice.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.datasourceservice.domain.DataSource;
import com.sjtu.project.datasourceservice.domain.DataSourceDao;
import com.sjtu.project.datasourceservice.domain.DataSourceFactory;
import com.sjtu.project.datasourceservice.domain.InputChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataSourceController {
    @Autowired
    DataSourceDao dataSourceDao;

    @GetMapping("DataSource")
    public Result<List<DataSource>> getAll() {
        return ResultUtil.success(dataSourceDao.findAllByVisibleIsTrue());
    }

    @PostMapping("DataSource")
    public Result<DataSource> addDataSource(@RequestBody DataSource dataSource) {
        return ResultUtil.success(dataSourceDao.save(DataSourceFactory.create(dataSource)));
    }

    @PostMapping("DataSource/{id}/Channel")
    public Result registerChannel(@PathVariable(name = "id") String id, @RequestBody InputChannel channel) {
        DataSource ds = dataSourceDao.queryById(id);
        ds.registerChannel(channel);
        return ResultUtil.success();
    }
}
