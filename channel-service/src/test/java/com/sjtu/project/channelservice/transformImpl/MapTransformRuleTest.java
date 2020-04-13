package com.sjtu.project.channelservice.transformImpl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/13 19:43
 */
@Slf4j
public class MapTransformRuleTest {
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
        Path source = new Path();
        source.setTargetKey("a");
        Path target = new Path();
        target.setTargetKey("b.a");
        MapTransformRule mapTransformRule = new MapTransformRule();
        mapTransformRule.getMapRule().put(source, target);
        ObjectNode res = mapTransformRule.doTransform(objectNode);
        Assert.assertNull(res.get("a"));
        Assert.assertEquals("1", res.get("b").get("a").asText());
        log.info("{}", res);
    }
}