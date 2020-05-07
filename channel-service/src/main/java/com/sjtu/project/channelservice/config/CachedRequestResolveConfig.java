package com.sjtu.project.channelservice.config;

import com.sjtu.project.channelservice.domain.BackPressureService;
import com.sjtu.project.channelservice.domain.InputChannel;
import com.sjtu.project.channelservice.domain.InputChannelDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/7 21:31
 */
@Configuration
@Slf4j
public class CachedRequestResolveConfig {
    @Autowired
    BackPressureService backPressureService;

    @Autowired
    InputChannelDao dao;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final ExecutorService multiExecutor = Executors.newCachedThreadPool();

    private final Set<String> runningThread = new ConcurrentSkipListSet<>();

    @Bean
    CommandLineRunner cachedRequestResolve() {
        return (args) -> {
            executor.submit(() -> {
                while (true) {
                    //TODO 处理缓存的请求
                    for (InputChannel inputChannel: dao.findAll()) {
                        String id = inputChannel.getId();
                        if (backPressureService.needResolveCachedRequest(inputChannel) && !runningThread.contains(id)) {
                            runningThread.add(id);
                            multiExecutor.submit(() -> {
                                log.info("开始处理缓存的请求");
                                while (backPressureService.needResolveCachedRequest(inputChannel)) {
                                    backPressureService.doDispatchWithBackPressure(inputChannel, inputChannel.getThreshold()).subscribe(x -> log.info("处理缓存请求{}条", x.getData()));
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                runningThread.remove(id);
                            });
                        }
                    }
                    Thread.sleep(10 * 60 * 1000) ;
                }
            });
        };
    }

}
