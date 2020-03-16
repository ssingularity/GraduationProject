package com.sjtu.project.coreservice.domain;

import java.util.List;

public interface ServiceManagement {
    List<DataSource> getDataSourceOf(String serviceId);
}
