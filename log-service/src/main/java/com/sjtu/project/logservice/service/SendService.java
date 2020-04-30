package com.sjtu.project.logservice.service;

import com.sjtu.project.logservice.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendService {
    @Value("${websocket.prefix}")
    private String prefix;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void send(Log log){
        messagingTemplate.convertAndSend(prefix + log.getProcessId(), log);
    }
}
