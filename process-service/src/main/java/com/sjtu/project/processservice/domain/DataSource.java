package com.sjtu.project.processservice.domain;

import com.sjtu.project.common.util.ContextUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSource {
    String id;

    public void register(InputChannel inputChannel) {
        ContextUtil.ctx.getBean(DataSourceServiceClient.class)
                .register(id, inputChannel);
    }
}
