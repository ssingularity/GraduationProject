package com.sjtu.project.common.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ObjectDescriptorTest {
    ObjectDescriptor objectDescriptor;

    @Before
    public void prepare() {
        objectDescriptor = new ObjectDescriptor();
        objectDescriptor.setKeyName("test");
        StringDescriptor stringDescriptor = new StringDescriptor();
        stringDescriptor.setKeyName("string");
        IntegerDescriptor integerDescriptor = new IntegerDescriptor();
        integerDescriptor.setKeyName("integer");
        objectDescriptor.setChildren(Arrays.asList(integerDescriptor, stringDescriptor));
    }

    @Test
    public void generateDataFromJson() {
        ObjectNode objectNode = JsonUtil.createObjectNode();
        ObjectNode child = JsonUtil.createObjectNode();
        child.put("string", "string");
        child.put("integer", "integer");
        objectNode.set("test", child);
        ObjectNode res = objectDescriptor.generateDataFromJson(objectNode);
        Assert.assertEquals(objectNode, res);
    }
}