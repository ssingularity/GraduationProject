package com.sjtu.project.servicemanagement.domain;

import com.sjtu.project.common.domain.RootDescriptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;

import java.util.List;

@Document
@Data
@Slf4j
public class Service {
    @Id
    String id;

    String name;

    RootDescriptor input;

    RootDescriptor output;

    List<DataSource> targetDataSource;

    String uri;

    HttpMethod method;

    //TODO 监控Service存活情况
    String healthEndPoint;

    public boolean verifySelf() {
        return true;
    }

    public void generateDataSource() {
        // TODO
        log.info("生成DataSource");
    }

    public void generateOutputChannel() {
        // TODO
        log.info("生成OutputChannel");
    }

    public void callWithMessage(String message) {
        
    }
}
