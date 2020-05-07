package com.sjtu.project.channelservice.adapter;

import com.sjtu.project.channelservice.domain.*;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class Controller {
    @Autowired
    InputChannelDao inputChannelDao;

    @Autowired
    ReactiveChannelDao reactiveChannelDao;

    @Autowired
    DataMapStorageService dataMapStorageService;

    @Autowired
    BackPressureService backPressureService;

    @PostMapping("/inputchannel/{id}/message")
    public Mono<Result> dispatchMessage(@PathVariable(name = "id") String id, @RequestBody Message message) {
        return reactiveChannelDao.findById(id)
                .flatMap(inputChannel -> inputChannel.onMessage(message));
    }

    @PostMapping("/inputchannel")
    public Result<InputChannel> addInputChannel(@RequestBody InputChannel inputChannel) {
        inputChannel = inputChannelDao.save(inputChannel);
        if (inputChannel.getFusionRule() != null) {
            dataMapStorageService.initChannel(inputChannel.getId(), inputChannel.getFusionRule().getDataSourceIdSet());
        }
        backPressureService.initChannel(inputChannel.getId());
        return ResultUtil.success(inputChannel);
    }

    @DeleteMapping("/inputchannel/{id}")
    public Result<String> deleteInputChannel(@PathVariable String id) {
        inputChannelDao.deleteById(id);
        dataMapStorageService.deleteChannel(id);
        backPressureService.deleteChannel(id);
        return ResultUtil.success();
    }
}
