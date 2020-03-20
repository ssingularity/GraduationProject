package com.sjtu.project.channelservice.adapter;

import com.sjtu.project.common.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {
    @PostMapping("channel/{id}/message")
    public void dispatchMessage(@PathVariable(name = "id") String id, @RequestBody Message message) {
        log.info("分发消息{}", message);
    }
}
