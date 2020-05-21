package com.sjtu.project.logservice.domain;


import lombok.Data;


@Data
public class DataSourceLog extends Log{
    private String dataSourceId;

    private String dataSourceName;
}