package com.sjtu.project.common.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringDescriptorTest {
    StringDescriptor stringDescriptor;

    @Before
    public void prepare() {
        stringDescriptor = new StringDescriptor();
        stringDescriptor.setDefaultValue("default");
        stringDescriptor.setKeyName("test");
    }

    @Test
    public void generateDataFromJson() {
        ObjectNode objectNode = JsonUtil.createObjectNode();
        objectNode.put("test", "test");
        ObjectNode res = stringDescriptor.generateDataFromJson(objectNode);
        Assert.assertEquals("test", res.get("test").asText());
    }

    @Test
    public void generateDataFromJsonWithDefault() {
        ObjectNode objectNode = JsonUtil.createObjectNode();
        objectNode.put("default", "test");
        ObjectNode res = stringDescriptor.generateDataFromJson(objectNode);
        Assert.assertEquals("default", res.get("test").asText());
    }
}