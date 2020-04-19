package com.sjtu.project.coreservice.domain;

import com.sjtu.project.coreservice.util.ContextUtils;
import lombok.Data;

@Data
public class DataSource {
    String id;

    public void register(InputChannel inputChannel) {
        ContextUtils.ctx.getBean(DataSourceService.class)
                .register(id, inputChannel);
    }
}
