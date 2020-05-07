package com.sjtu.project.channelservice.serviceImpl;

import com.sjtu.project.channelservice.domain.BackPressureService;
import com.sjtu.project.channelservice.domain.InputChannel;
import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/7 12:00
 */
@Service
@Slf4j
public class BackPressureServiceImpl implements BackPressureService {
    private static final String WAITING_QUEUE_PREFIX = "WaitingQueue:";

    private static final String EXECUTING_QUEUE_PREFIX = "ExecutingQueue:";

    @PostConstruct
    public void initCacheResolveThread() {
    }

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void initChannel(String channelId) {

    }

    @Override
    public Mono<Result> dispatchContentForChannel(InputChannel inputChannel, String content) {
        String waitingQueueKey = generateWaitingQueueKey(inputChannel.getId());
        String executingQueueKey = generateExecutingQueueKey(inputChannel.getId());
        redisTemplate.boundListOps(waitingQueueKey).rightPush(content);
        long executingQueueSize = redisTemplate.boundListOps(executingQueueKey).size();
        if (executingQueueSize >= inputChannel.getThreshold()) {
            return Mono.just(ResultUtil.success());
        }
        else {
            long diff = inputChannel.getThreshold() - executingQueueSize;
            long waitingQueueSize = redisTemplate.boundListOps(waitingQueueKey).size();
            int num = Math.min((int)diff, (int)waitingQueueSize);
            return doDispatchWithBackPressure(inputChannel, num);
        }
    }

    @Override
    public Boolean needResolveCachedRequest(InputChannel inputChannel) {
        String waitingQueueKey = generateWaitingQueueKey(inputChannel.getId());
        return redisTemplate.boundListOps(waitingQueueKey).size() != 0;
    }

    @Override
    public Mono<Result> doDispatchWithBackPressure(InputChannel inputChannel, int num) {
        String waitingQueueKey = generateWaitingQueueKey(inputChannel.getId());
        String executingQueueKey = generateExecutingQueueKey(inputChannel.getId());
        return Flux.range(0, num)
                .map(x -> {
                    String res = redisTemplate.boundListOps(waitingQueueKey).leftPop();
                    return res == null ? "" : res;
                })
                .filter(StringUtils::isNotEmpty)
                .flatMap(x -> inputChannel.doDispatch(x)
                        .doFirst(() -> redisTemplate.boundListOps(executingQueueKey).rightPush(x))
                        .doFinally((y) -> redisTemplate.boundListOps(executingQueueKey).leftPop())
                )
                .flatMap(inputChannel::doDispatch2DataSource)
                .count()
                .map(ResultUtil::success);
    }

    @Override
    public void deleteChannel(String channelId) {
        redisTemplate.delete(generateWaitingQueueKey(channelId));
        redisTemplate.delete(generateExecutingQueueKey(channelId));
    }

    private String generateWaitingQueueKey(String channelId) {
        return WAITING_QUEUE_PREFIX + channelId;
    }

    private String generateExecutingQueueKey(String channelId) {
        return EXECUTING_QUEUE_PREFIX + channelId;
    }
}
