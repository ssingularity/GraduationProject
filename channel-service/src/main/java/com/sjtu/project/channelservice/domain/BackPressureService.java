package com.sjtu.project.channelservice.domain;

import com.sjtu.project.common.response.Result;
import reactor.core.publisher.Mono;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/7 11:55
 */
public interface BackPressureService {
    /**
     * 为Channel分配对应的WaitingQueue以及ExecutingQueue的空间
     * @param channelId channelId
     */
    void initChannel(String channelId);

    /**
     * 为Channel采用背压控制的方式分发数据
     * @param inputChannel 输入通道
     * @param content 分发的数据
     * @return Mono<Result>
     */
    Mono<Result> dispatchContentForChannel(InputChannel inputChannel, String content);

    /**
     * 清除为Channel分配的空间
     * @param channelId channelId
     */
    void deleteChannel(String channelId);

    /**
     * 判断是否有缓存的请求
     * @param inputChannel inpuChannel
     * @return Boolean
     */
    Boolean needResolveCachedRequest(InputChannel inputChannel);

    /**
     * 基于背压机制进行转发
     * @param inputChannel inputChannel
     * @param num 转发的请求数
     * @return Mono<Result>
     */
    Mono<Result> doDispatchWithBackPressure(InputChannel inputChannel, int num);

    /**
     * 获取channel的执行队列
     * @param channelId channelId
     * @return Long
     */
    Long getExecutingQueueSize(String channelId);

    /**
     * 获取channel的等待列
     * @param channelId channelId
     * @return Long
     */
    Long getWaitingQueueSize(String channelId);
}
