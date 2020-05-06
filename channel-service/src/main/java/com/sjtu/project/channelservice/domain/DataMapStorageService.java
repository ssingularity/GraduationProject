package com.sjtu.project.channelservice.domain;

import java.util.Map;
import java.util.Set;

/**
 * DataMapStorageService
 *
 */
public interface DataMapStorageService {

    /**
     * 为指定channelId和对应dataSourceId集合初始化存储空间
    **/
     void initChannel(String channelId, Set<String> dataSourceIdSet);

    /**
     * 新增数据
     **/
    boolean putObj(String channelId, String dataSourceId, Object key, Object obj);

    /**
     * 判断新增数据对应的key是否在所有dataSourceId的Map中都有数据，如果有则以Map<dataSourceId,obj>的格式弹出数据
     **/
    Map<String, String> putAndPopCurrentSrcObjMapIfFull(String channelId, String dataSourceId, String key, String content);
}
