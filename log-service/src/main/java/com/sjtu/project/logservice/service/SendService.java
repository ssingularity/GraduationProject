package com.sjtu.project.logservice.service;

import com.sjtu.project.common.util.JsonUtil;
import com.sjtu.project.logservice.config.WebSocketHandler;
import com.sjtu.project.logservice.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendService {

    @Autowired
    private WebSocketHandler handler;

    public void send(Log log){
        try{
            handler.sendMessage(log.getProcessId(), JsonUtil.writeValueAsString(log));
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
