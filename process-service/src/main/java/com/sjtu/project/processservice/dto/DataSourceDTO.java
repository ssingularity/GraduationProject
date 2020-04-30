package com.sjtu.project.processservice.dto;

import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.processservice.domain.DataSourceServiceClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceDTO {
    String id;

    public void register(InputChannelDTO inputChannelDTO) {
        ContextUtil.ctx.getBean(DataSourceServiceClient.class)
                .register(id, inputChannelDTO);
    }
}
