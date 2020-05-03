package com.sjtu.project.common.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GraduationApplication {
}
