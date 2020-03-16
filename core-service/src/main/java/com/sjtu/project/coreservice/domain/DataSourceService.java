package com.sjtu.project.coreservice.domain;

import java.util.List;

public interface DataSourceService {
    List<DataSource> getDataSourceOf(String serviceId);

    void register(String dataSourceId, InputChannel inputChannel);

    void unRegister(String dataSourceId, InputChannel inputChannel);
}
