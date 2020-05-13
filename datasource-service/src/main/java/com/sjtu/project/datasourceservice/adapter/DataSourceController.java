package com.sjtu.project.datasourceservice.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.datasourceservice.domain.AclService;
import com.sjtu.project.datasourceservice.domain.DataSource;
import com.sjtu.project.datasourceservice.domain.DataSourceDao;
import com.sjtu.project.datasourceservice.domain.DataSourceService;
import com.sjtu.project.datasourceservice.dto.InputChannelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/datasource/batch")
    public Result<List<DataSource>> getBatch(@RequestBody List<String> dataSourceIdList) {
        return ResultUtil.success(dataSourceDao.queryByIdIn(dataSourceIdList));
    }

    @GetMapping("/datasource/mine")
    public Result<List<DataSource>> getAllByUserName(@RequestParam(name = "username") String username) {
        return ResultUtil.success(dataSourceDao.findAllByVisibleIsTrueAndOwner(username));
    }

    @PostMapping("/datasource")
    public Result<DataSource> addDataSource(@Valid @RequestBody DataSource dataSource) {
        return ResultUtil.success(dataSourceDao.save(dataSourceService.create(dataSource)));
    }

    @PostMapping("/datasource/{id}/channel")
    public Result<String> registerChannel(@PathVariable(name = "id") String id, @RequestBody InputChannelDTO channel) {
        DataSource ds = dataSourceDao.queryById(id);
        ds.registerChannel(channel);
        return ResultUtil.success();
    }


    @PostMapping("/datasource/{id}/message")
    public Result<String> sendMessage(@PathVariable("id") String datasourceId, @RequestBody String message) {
        DataSource ds = dataSourceDao.queryById(datasourceId);
        ds.sendMessage(message);
        return ResultUtil.success(message);
    }

    @PostMapping("/datasource/{dsId}/user/{username}")
    public Result<String> authorize(@PathVariable(name = "dsId") String dsId,
                                    @PathVariable(name = "username") String username) {
        DataSource ds = dataSourceDao.queryById(dsId);
        aclService.authorize(username, ds);
        return ResultUtil.success();
    }

    @DeleteMapping("/datasource/{dsId}/channel/{channelId}")
    public Result<String> unregisterChannel(@PathVariable(name = "dsId") String dsId,
                                           @PathVariable(name = "channelId") String channelId) {
        DataSource ds = dataSourceDao.queryById(dsId);
        ds.unregisterChannel(channelId);
        if (ds.registeredChannels().size() == 0 && !ds.isVisible()) {
            dataSourceService.delete(ds);
        }
        return ResultUtil.success();
    }

    @DeleteMapping("/datasource/{dsId}")
    public Result<String> deleteDataSource(@PathVariable(name = "dsId") String dsId) {
        DataSource ds = dataSourceDao.findOneById(dsId);
        if (ds != null) {
            dataSourceService.delete(ds);
        }
        return ResultUtil.success();
    }
}
