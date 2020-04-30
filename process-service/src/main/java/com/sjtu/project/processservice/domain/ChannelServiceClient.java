package com.sjtu.project.processservice.domain;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.processservice.dto.InputChannelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "channel-service")
public interface ChannelServiceClient {

    @PostMapping("/inputchannel")
    Result<InputChannelDTO> createInputChannel(@RequestBody InputChannelDTO channelDTO);
}
