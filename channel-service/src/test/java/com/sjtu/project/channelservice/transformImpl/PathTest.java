package com.sjtu.project.channelservice.transformImpl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/13 18:02
 */
public class PathTest {
    ObjectNode objectNode;

    @Before
    public void init() {
        objectNode = JsonUtil.createObjectNode();
        objectNode.put("a", 1);
        ObjectNode tmp = JsonUtil.createObjectNode();
        tmp.put("b", 1);
        tmp.put("c", 1);
        objectNode.set("b", tmp);
    }

    @Test
    public void getValueFromJson() {
        Path path = new Path();
        path.setTargetKey("b.b");
        Assert.assertEquals("1", path.getValueFromJson(objectNode));
    }

    @Test
    public void deleteFromJson() {
        Path path = new Path();
        path.setTargetKey("b.b");
        path.deleteFromJson(objectNode);
        Assert.assertNull(path.getValueFromJson(objectNode));
    }

    @Test
    public void testSetValueToFullyNewPath() {
        Path path = new Path();
        path.setTargetKey("c.c");
        path.setValueToJson(objectNode, "1");
        Assert.assertEquals("1", path.getValueFromJson(objectNode));
    }

    @Test
    public void testSetValueToOldPath() {
        Path path = new Path();
        path.setTargetKey("b.b");
        path.setValueToJson(objectNode, "2");
        Assert.assertEquals("2", path.getValueFromJson(objectNode));
    }

    @Test
    public void testSetValueToHalfOldPath() {
        Path path = new Path();
        path.setTargetKey("b.d.c");
        path.setValueToJson(objectNode, "2");
        Assert.assertEquals("2", path.getValueFromJson(objectNode));
    }
}