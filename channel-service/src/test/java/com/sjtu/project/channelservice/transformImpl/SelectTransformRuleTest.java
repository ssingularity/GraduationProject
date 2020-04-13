package com.sjtu.project.channelservice.transformImpl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/13 19:49
 */
@Slf4j
public class SelectTransformRuleTest {
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
    public void doTransform() {
        Path selectedPath = new Path();
        selectedPath.setTargetKey("b.c");
        SelectTransformRule selectTransformRule = new SelectTransformRule();
        selectTransformRule.getExpectedKeys().add(selectedPath);
        ObjectNode res = selectTransformRule.doTransform(objectNode);
        Assert.assertNull(res.get("a"));
        Assert.assertNull(res.get("b").get("b"));
        Assert.assertEquals("1", res.get("b").get("c").asText());
    }
}