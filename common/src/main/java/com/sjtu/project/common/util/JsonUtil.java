package com.sjtu.project.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String writeValueAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T readVaules(String json, Class<T> clazz) {
        try{
            return objectMapper.readValue(json, clazz);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
