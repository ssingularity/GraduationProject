package com.sjtu.project.channelservice.adapter;

import com.sjtu.project.channelservice.domain.InputChannel;
import com.sjtu.project.channelservice.domain.InputChannelDao;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {
    //TODO AOP作日志切面

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
}
