package com.sjtu.project.channelservice.serviceImpl;

import com.sjtu.project.channelservice.domain.DataMapStorageService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/6 12:25
 */
@Service
public class DataMapStorageServiceImpl implements DataMapStorageService {
    @Override
    public void initChannel(String channelId, Set<String> dataSourceIdSet) {

    }

    @Override
    public boolean putObj(String channelId, String dataSourceId, Object key, Object obj) {
        return false;
    }

    @Override
    public Map<String, String> putAndPopCurrentSrcObjMapIfFull(String channelId, String dataSourceId, String key, String content) {
        return null;
    }
}
