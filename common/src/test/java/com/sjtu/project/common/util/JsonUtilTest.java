package com.sjtu.project.common.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class JsonUtilTest {

    @Test
    public void readTree() {
        String json = "{\"what\": \"123\"}";
        ObjectNode node = JsonUtil.readTree(json);
        Assert.assertEquals("123", node.get("what").asText());
    }

    @Test
    public void contact() {
        ObjectNode node1 = JsonUtil.createObjectNode();
        node1.put("test1", "test1");
        ObjectNode node2 = JsonUtil.createObjectNode();
        node2.put("test2", "test2");
        ObjectNode expect = JsonUtil.createObjectNode();
        expect.put("test1", "test1");
        expect.put("test2", "test2");
        Assert.assertEquals(expect, JsonUtil.contact(Arrays.asList(node1, node2)));
    }
}