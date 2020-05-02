package com.sjtu.project.datasourceservice.domain;


import com.sjtu.project.common.domain.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "channel-service")
public interface ChannelService {
    @RequestMapping(value = "/inputchannel/{channelId}/message", method = RequestMethod.POST)
    @ResponseBody
    void dispatchMessage(@PathVariable(name = "channelId") String channelId, @RequestBody Message message);
}
