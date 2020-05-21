package com.sjtu.project.channelservice.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FusionRule {
    String name;

    String description;

    String keyName;

    List<String> dataSourceIdSet;

    public String doFusion(String channelId, Message message) {
        DataMapStorageService dataMapStorageService = ContextUtil.ctx.getBean(DataMapStorageService.class);
        String content = message.getContent();
        ObjectNode objectNode = JsonUtil.readTree(content);
        String key = objectNode.get(keyName).asText();
        dataMapStorageService.putObj(channelId, message.getDataSourceId(), key, content);
        Map<String, String> map = dataMapStorageService.popCurrentSrcObjMapIfFull(channelId, key);
        if (map != null) {
            ObjectNode res = JsonUtil.createObjectNode();
            res.put(keyName, key);
            for (String value: map.values()) {
                ObjectNode tmp = JsonUtil.readTree(value);
                tmp.remove(keyName);
                res.setAll(tmp);
            }
            return JsonUtil.writeValueAsString(res);
        }
        return null;
    }
}
