package com.sjtu.project.servicemanagement.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/3 17:20
 */
@Data
public class ServiceDTO {
    String id;

    String name;

    String description;

    String ip;

    Integer port;

    String path;

    HttpMethod method;
}
