package com.sjtu.project.processservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjtu.project.common.util.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/13 15:28
 */
@Configuration
public class JacksonObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtil.getObjectMapper();
    }
}
