package com.sjtu.project.servicemanagement;

import com.sjtu.project.common.annotation.GraduationApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@GraduationApplication
@EnableWebFlux
public class ServiceManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceManagementApplication.class, args);
    }
}
