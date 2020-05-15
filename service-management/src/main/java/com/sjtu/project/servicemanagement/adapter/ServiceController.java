package com.sjtu.project.servicemanagement.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.servicemanagement.domain.ReactiveServiceDao;
import com.sjtu.project.servicemanagement.domain.Service;
import com.sjtu.project.servicemanagement.domain.ServiceDao;
import com.sjtu.project.servicemanagement.domain.ServiceFactory;
import com.sjtu.project.servicemanagement.dto.DataSourceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
public class ServiceController {
    @Autowired
    ServiceDao serviceDao;

    @Autowired
    ReactiveServiceDao reactiveServiceDao;

    @GetMapping("/service")
    public Result<List<Service>> getAll() {
        return ResultUtil.success(serviceDao.findAll());
    }

    @GetMapping("/service/{id}")
    public Result<Service> getOneById(@PathVariable(name = "id") String id) {
        return ResultUtil.success(serviceDao.queryById(id));
    }

    @PostMapping("/service")
    public Result<Service> addService(@RequestBody Service service) {
        return ResultUtil.success(serviceDao.save(ServiceFactory.createService(service)));
    }

    @PostMapping("/service/{id}/datasource")
    public Result<DataSourceDTO> createDataSource(@PathVariable(name = "id") String id) {
        Service service = serviceDao.queryById(id);
        return ResultUtil.success(service.generateDataSource());
    }

    @PostMapping("/service/{id}/message")
    public Mono<Result<String>> call(@PathVariable(name = "id") String id, @RequestBody String message) {
        return reactiveServiceDao.findById(id)
                .flatMap(service -> service.invokeWith(message))
                .map(ResultUtil::success);
    }
}
