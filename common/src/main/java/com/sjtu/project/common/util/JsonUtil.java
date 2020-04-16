package com.sjtu.project.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String writeValueAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T readValues(String json, Class<T> clazz) {
        try{
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObjectNode readTree(String json) {
        try {
            return (ObjectNode) objectMapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObjectNode contact(List<ObjectNode> objectNodes) {
        ObjectNode res = objectMapper.createObjectNode();
        for (ObjectNode objectNode : objectNodes) {
            res.setAll(objectNode);
        }
        return res;
    }

    public static ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return objectMapper.createArrayNode();
    }

    public static void register(Class clazz) {
        log.info("注册了class: {}", clazz);
        objectMapper.registerSubtypes(clazz);
    }
}
