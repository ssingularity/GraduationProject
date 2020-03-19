package com.sjtu.project.datasourceservice.domain;

public interface DataSourceListener {
    void onMessage(String dsId, String message);
}
