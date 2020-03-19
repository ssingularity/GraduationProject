package com.sjtu.project.datasourceservice.domain;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class DataSourceFactory {
    @PostConstruct
    public void wakeUpDeadDataSource() {
        DataSourceDao dataSourceDao = Constants.ctx.getBean(DataSourceDao.class);
        DataSourceListener dataSourceListener = Constants.ctx.getBean(DataSourceListener.class);
        dataSourceDao.findAll().forEach(ds -> ds.startWithListener(dataSourceListener));
    }

    public static DataSource create(DataSource dataSource) {
        dataSource.verifySelf();
        dataSource.setId(UUID.randomUUID().toString());
        dataSource.startWithListener(Constants.ctx.getBean(DataSourceListener.class));
        return dataSource;
    }
}
