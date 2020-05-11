package com.sjtu.project.userservice.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Document(collection = "permissionInfo")
public class PermissionInfo {
    @Id
    private String id;

    private String username;

    private List<String> dataSourceIds = new ArrayList<>();

    public void addDataSourceId(String dataSourceId) {
        this.dataSourceIds.add(dataSourceId);
    }
}
