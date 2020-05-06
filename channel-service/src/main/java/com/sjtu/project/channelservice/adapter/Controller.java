package com.sjtu.project.channelservice.adapter;

import com.sjtu.project.channelservice.domain.DataMapStorageService;
import com.sjtu.project.channelservice.domain.InputChannel;
import com.sjtu.project.channelservice.domain.InputChannelDao;
import com.sjtu.project.channelservice.domain.ReactiveChannelDao;
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
        return ResultUtil.success(inputChannel);
    }

    @DeleteMapping("/inputchannel/{id}")
    public Result<String> deleteInputChannel(@PathVariable String id) {
        inputChannelDao.deleteById(id);
        dataMapStorageService.deleteChannel(id);
        return ResultUtil.success();
    }
}
