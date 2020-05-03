package com.sjtu.project.channelservice;

import com.sjtu.project.common.annotation.GraduationApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@GraduationApplication
@EnableWebFlux
public class ChannelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChannelServiceApplication.class, args);
    }
}
