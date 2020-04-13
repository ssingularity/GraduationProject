package com.sjtu.project.common.configure;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sjtu.project.common.util.JsonUtil;
import org.reflections.Reflections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/13 15:37
 */
@Configuration
public class AnnotationProcessorConfiguration {
    @Bean
    CommandLineRunner annotationProcessor() {
        return args -> {
            Reflections reflections = new Reflections("com.sjtu.project");
            reflections.getTypesAnnotatedWith(JsonTypeName.class)
                    .forEach(JsonUtil::register);
        };
    }
}
