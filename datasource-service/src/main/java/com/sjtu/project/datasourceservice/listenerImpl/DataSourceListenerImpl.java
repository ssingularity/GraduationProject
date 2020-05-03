package com.sjtu.project.datasourceservice.listenerImpl;

import com.sjtu.project.datasourceservice.domain.ChannelService;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.datasourceservice.domain.DataSource;
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
    public void onMessage(DataSource ds, String message) {
        Set<String> registerChannelIds = ds.registeredChannels();
        if (registerChannelIds != null) {
            for (String channelId : registerChannelIds) {
                log.info("分发给id为{}的通道", channelId);
                channelService.dispatchMessage(channelId, new Message(ds.getId(), message)).subscribe();
            }
        }
    }
}
