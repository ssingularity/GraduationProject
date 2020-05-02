package com.sjtu.project.channelservice.adapter;

import com.sjtu.project.channelservice.domain.InputChannel;
import com.sjtu.project.channelservice.domain.InputChannelDao;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class Controller {
    @Autowired
    InputChannelDao inputChannelDao;

    @PostMapping("/inputchannel/{id}/message")
    public void dispatchMessage(@PathVariable(name = "id") String id, @RequestBody Message message) {
        InputChannel inputChannel = inputChannelDao.queryOneById(id);
        inputChannel.onMessage(message);
    }

    @PostMapping("/inputchannel")
    public Result<InputChannel> addInputChannel(@RequestBody InputChannel inputChannel) {
        return ResultUtil.success(inputChannelDao.save(inputChannel));
    }

    @DeleteMapping("/inputchannel/{id}")
    public Result<String> deleteInputChannel(@PathVariable String id) {
        inputChannelDao.deleteById(id);
        return ResultUtil.success();
    }
}
