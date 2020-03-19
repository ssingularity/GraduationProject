package com.sjtu.project.datasourceservice.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Constants implements ApplicationContextAware {
    public static ApplicationContext ctx;

    public static String KAFKA_URL;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Constants.ctx = applicationContext;
        Constants.KAFKA_URL = applicationContext.getEnvironment().getProperty("custom.kafka.url");
    }
}