package com.sjtu.project.logservice.service;


import com.sjtu.project.logservice.dao.LogRepository;
import com.sjtu.project.logservice.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    @Autowired
    private SendService sendService;

    public Log createLog(Log log) {
        log.setTimestamp(new Date());
        sendService.send(log);
        return logRepository.save(log);
    }

    public List<Log> createLogs(List<Log> logs) {
        List<Log> result = null;
        for(Log log:logs){
            log.setTimestamp(new Date());
            sendService.send(log);
            result.add(logRepository.save(log));
        }
        return result;
    }
}
