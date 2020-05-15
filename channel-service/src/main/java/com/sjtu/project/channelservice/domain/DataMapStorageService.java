package com.sjtu.project.channelservice.domain;

import java.util.List;
import java.util.Map;

/**
 * DataMapStorageService
 *
 */
public interface DataMapStorageService {

    /**
     * 为指定channelId和对应dataSourceId集合初始化存储空间
    **/
     void initChannel(String channelId, List<String> dataSourceIdSet);

    /**
     * 为指定channelId删除对应的存储空间
     **/
    void deleteChannel(String channelId);

    /**
     * 获取数据
     **/
    String getObj(String channelId, String dataSourceId, String key);

    /**
     * 新增数据
     **/
    void putObj(String channelId, String dataSourceId, String key, String obj);

    /**
     * 判断key是否在所有dataSourceId的Map中都有数据，如果有则以Map<dataSourceId,obj>的格式弹出数据
     **/
    Map<String, String> popCurrentSrcObjMapIfFull(String channelId, String key);
}
