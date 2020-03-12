package com.sjtu.project.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String writeValueAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
