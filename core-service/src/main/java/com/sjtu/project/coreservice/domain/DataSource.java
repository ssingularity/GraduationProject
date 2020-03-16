package com.sjtu.project.coreservice.domain;

import lombok.Data;

@Data
public class DataSource {
    String id;

    public void register(InputChannel inputChannel) {
        Constants.ctx.getBean(DataSourceService.class)
                .register(id, inputChannel);
    }
}
