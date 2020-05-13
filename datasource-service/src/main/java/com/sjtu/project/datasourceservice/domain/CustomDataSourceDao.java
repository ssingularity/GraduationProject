package com.sjtu.project.datasourceservice.domain;

import java.util.List;

public interface CustomDataSourceDao {
    DataSource queryById(String id);

    List<DataSource> queryByIdIn(List<String> ids);
}
