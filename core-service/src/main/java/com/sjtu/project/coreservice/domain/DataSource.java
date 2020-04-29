package com.sjtu.project.coreservice.domain;

import com.sjtu.project.common.util.ContextUtil;
import lombok.Data;

@Data
public class DataSource {
    String id;

    public void register(InputChannel inputChannel) {
        ContextUtil.ctx.getBean(DataSourceServiceClient.class)
                .register(id, inputChannel);
    }
}
