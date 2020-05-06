package com.sjtu.project.channelservice.serviceImpl;

import com.sjtu.project.channelservice.ChannelServiceApplication;
import com.sjtu.project.channelservice.domain.DataMapStorageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/6 23:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChannelServiceApplication.class)
public class DataMapStorageServiceImplTest {
    @Autowired
    DataMapStorageService dataMapStorageService;

    @Test
    public void test() {
        dataMapStorageService.initChannel("a", new HashSet<>(Arrays.asList("a", "b")));
        dataMapStorageService.putObj("a", "a", "a", "test1");
        Map<String, String> res = dataMapStorageService.popCurrentSrcObjMapIfFull("a", "a");
        Assert.assertNull(res);
        dataMapStorageService.putObj("a", "b", "a", "test2");
        dataMapStorageService.putObj("a", "a", "b", "test1");
        res = dataMapStorageService.popCurrentSrcObjMapIfFull("a", "a");
        Assert.assertEquals("test1", res.get("a"));
        Assert.assertEquals("test2", res.get("b"));
        Assert.assertNull(dataMapStorageService.getObj("a", "a", "a"));
        Assert.assertNull(dataMapStorageService.getObj("a", "b", "a"));
        Assert.assertNotNull(dataMapStorageService.getObj("a", "a", "b"));
    }
}