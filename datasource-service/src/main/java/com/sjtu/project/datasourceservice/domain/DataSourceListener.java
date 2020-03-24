package com.sjtu.project.datasourceservice.domain;

public interface DataSourceListener {
    void onMessage(DataSource ds, String message);
}
