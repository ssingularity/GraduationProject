package com.sjtu.project.channelservice.transformImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/13 17:45
 */
@Data
public class Path {
    final private static String SPLITTER = "\\.";

    private String targetKey;

    public String getValueFromJson(ObjectNode objectNode) {
        String[] keys = targetKey.split(SPLITTER);
        JsonNode tmp = objectNode;
        for (String key : keys) {
            tmp = tmp.get(key);
            if (tmp == null) {
                return null;
            }
        }
        return tmp.asText();
    }

    public void deleteFromJson(ObjectNode objectNode) {
        String[] keys = targetKey.split(SPLITTER);
        for (int i = 0; i < keys.length - 1; ++i) {
            objectNode = (ObjectNode) objectNode.get(keys[i]);
        }
        objectNode.remove(keys[keys.length - 1]);
    }

    public void setValueToJson(ObjectNode objectNode, String value) {
        String[] keys = targetKey.split(SPLITTER);
        int index = findFirstIndexOfNullObjectNode(objectNode, keys);
        if (index == keys.length - 1 || index == keys.length) {
            for (int i = 0; i < keys.length - 1; ++i) {
                objectNode = (ObjectNode) objectNode.get(keys[i]);
            }
            objectNode.put(keys[keys.length - 1], value);
        } else {
            ObjectNode insertedNode = createNode(keys, index + 1, value);
            setObjectNode(keys, index, objectNode, insertedNode);
        }
    }

    private void setObjectNode(String[] keys, int index, ObjectNode objectNode, ObjectNode insertedNode) {
        for (int i = 0; i < index; ++i) {
            objectNode = (ObjectNode) objectNode.get(keys[i]);
        }
        objectNode.set(keys[index], insertedNode);
    }

    private ObjectNode createNode(String[] keys, int index, String value) {
        ObjectNode res = JsonUtil.createObjectNode();
        if (index == keys.length - 1) {
            res.put(keys[index], value);
            return res;
        } else {
            res.set(keys[index], createNode(keys, index + 1, value));
            return res;
        }
    }

    private int findFirstIndexOfNullObjectNode(ObjectNode objectNode, String[] keys) {
        JsonNode tmp = objectNode;
        for (int i = 0; i < keys.length - 1; ++i) {
            if (tmp.get(keys[i]) == null) {
                return i;
            }
            tmp = tmp.get(keys[i]);
        }
        return keys.length;
    }
}
