package com.sjtu.project.coreservice.domain;

public interface DataSourceService {
    void register(String dataSourceId, InputChannel inputChannel);

    void unRegister(String dataSourceId, InputChannel inputChannel);
}
