package com.sjtu.project.processservice.domain;

import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.processservice.dto.InputChannelDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DataSource {
    String id;

    public void register(InputChannelDTO inputChannelDTO) {
        ContextUtil.ctx.getBean(DataSourceServiceClient.class)
                .registerChannel(id, inputChannelDTO);
    }

    public void unregister(String channelId) {
        ContextUtil.ctx.getBean(DataSourceServiceClient.class)
                .unregisterChannel(id, channelId);
    }
}
