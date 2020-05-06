package com.sjtu.project.channelservice.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class FusionRule {
    String name;

    String description;

    String keyName;

    Set<String> dataSourceIdSet;

    public String doFusion(String channelId, Message message) {
        String content = message.getContent();
        ObjectNode objectNode = JsonUtil.readTree(content);
        String key = objectNode.get(keyName).asText();
        Map<String, String> map = ContextUtil.ctx.getBean(DataMapStorageService.class)
                .putAndPopCurrentSrcObjMapIfFull(channelId, message.getDatasourceId(), key, content);
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
