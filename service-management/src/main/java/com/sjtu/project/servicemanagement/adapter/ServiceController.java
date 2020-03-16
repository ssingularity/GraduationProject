package com.sjtu.project.servicemanagement.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.servicemanagement.domain.DataSource;
import com.sjtu.project.servicemanagement.domain.Service;
import com.sjtu.project.servicemanagement.domain.ServiceDao;
import com.sjtu.project.servicemanagement.domain.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServiceController {
    @Autowired
    ServiceDao serviceDao;

    @GetMapping("service")
    public Result<List<Service>> getAll() {
        return ResultUtil.success(serviceDao.findAll());
    }

    @PostMapping("service")
    public Result<Service> addService(@RequestBody Service service) {
        return ResultUtil.success(serviceDao.save(ServiceFactory.createService(service)));
    }

    @GetMapping("service/{id}/datasource")
    public Result<List<DataSource>> getDataSource(@PathVariable(name = "id") String id) {
        Service service = serviceDao.queryById(id);
        return ResultUtil.success(service.getTargetDataSource());
    }
}
