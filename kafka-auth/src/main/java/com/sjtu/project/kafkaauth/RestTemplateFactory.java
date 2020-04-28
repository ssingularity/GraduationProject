package com.sjtu.project.kafkaauth;

import org.springframework.web.client.RestTemplate;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/26 13:56
 */
public class RestTemplateFactory {
    private static RestTemplate restTemplate;

    public static RestTemplate getInstance() {
        if (restTemplate == null) {
            synchronized (RestTemplateFactory.class) {
                if (restTemplate == null) {
                    restTemplate = new RestTemplate();
                    restTemplate.setErrorHandler(new NoErrorResultErrorHandler());
                }
            }
        }
        return restTemplate;
    }
}
