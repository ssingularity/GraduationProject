package com.sjtu.project.servicemanagement.adapter;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.servicemanagement.domain.*;
import com.sjtu.project.servicemanagement.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ServiceController {
    @Autowired
    ServiceDao serviceDao;

    @Autowired
    ReactiveServiceDao reactiveServiceDao;

    @GetMapping("/service")
    public Result<List<ServiceDTO>> getAll() {
        List<ServiceDTO> res = serviceDao.findAll()
                .stream()
                .map(Service::convert2DTO)
                .collect(Collectors.toList());
        return ResultUtil.success(res);
    }

    @PostMapping("/service")
    public Result<Service> addService(@RequestBody Service service) {
        return ResultUtil.success(serviceDao.save(ServiceFactory.createService(service)));
    }

    @PostMapping("/service/{id}/datasource")
    public Result<DataSource> createDataSource(@PathVariable(name = "id") String id) {
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
