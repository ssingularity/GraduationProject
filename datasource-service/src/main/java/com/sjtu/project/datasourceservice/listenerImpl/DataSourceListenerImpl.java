package com.sjtu.project.datasourceservice.listenerImpl;

import com.sjtu.project.datasourceservice.domain.DataSourceListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSourceListenerImpl implements DataSourceListener {
    @Autowired
    StringRedisTemplate template;

    @Override
    public void onMessage(String dsId, String message) {
        log.info("id为{}的数据源接收到了数据{}", dsId, message);
    }
}
