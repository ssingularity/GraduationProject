package com.sjtu.project.datasourceservice.listenerImpl;

import com.sjtu.project.datasourceservice.domain.ChannelService;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.datasourceservice.domain.DataSourceListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class DataSourceListenerImpl implements DataSourceListener {
    @Autowired
    StringRedisTemplate template;

    @Autowired
    ChannelService channelService;

    @Override
    public void onMessage(String dsId, String message) {
        log.info("id为{}的数据源接收到了数据{}", dsId, message);
        Set<String> registerChannelIds = template.boundSetOps(dsId).members();
        if (registerChannelIds != null) {
            for (String channelId : registerChannelIds) {
                log.info("分发给id为{}的通道Id", channelId);
                channelService.dispatchMessage(channelId, new Message(dsId, message));
            }
        }
    }
}
