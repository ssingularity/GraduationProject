package com.sjtu.project.channelservice.aspect;

import com.sjtu.project.channelservice.domain.BackPressureService;
import com.sjtu.project.channelservice.domain.InputChannel;
import com.sjtu.project.common.domain.Message;
import com.sjtu.project.common.util.ContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/30 21:53
 */
@Aspect
public class LogAspect {
    @Pointcut("execution(public * com.sjtu.project.channelservice.domain.InputChannel.onMessage(..))")
    public void dataSourceInvoke() {
    }

    @Pointcut("execution(public * com.sjtu.project.channelservice.domain.InputChannel.doDispatch(..))")
    public void serviceInvoke() {
    }

    @Before(value = "dataSourceInvoke()")
    public void dataSourceLog(JoinPoint joinPoint) {
        InputChannel inputChannel = (InputChannel) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        Message message = (Message) args[0];
        if (!message.getDataSourceName().startsWith("Invisible")) {
            LogDTO logDTO = LogDTO.builder()
                    .type("DataSource")
                    .content(message.getContent())
                    .dataSourceId(message.getDataSourceId())
                    .dataSourceName(message.getDataSourceName())
                    .processId(inputChannel.getProcessId())
                    .timestamp(new Date())
                    .build();
            ContextUtil.ctx.getBean(LogServiceClient.class).createLog(logDTO);
        }
    }

    @Before("serviceInvoke()")
    public void serviceLog(JoinPoint joinPoint) {
        InputChannel inputChannel = (InputChannel) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        String content = (String) args[0];
        Long executingQueueSize = ContextUtil.ctx.getBean(BackPressureService.class).getExecutingQueueSize(inputChannel.getId()) + 1;
        Long waitingQueueSize = ContextUtil.ctx.getBean(BackPressureService.class).getWaitingQueueSize(inputChannel.getId());
        LogDTO logDTO = LogDTO.builder()
                .type("Service")
                .content(content)
                .serviceId(inputChannel.getTargetServiceId())
                .serviceName(inputChannel.getTargetServiceName())
                .executingQueueSize(executingQueueSize > inputChannel.getThreshold() ? inputChannel.getThreshold() : executingQueueSize)
                .waitingQueueSize(waitingQueueSize)
                .processId(inputChannel.getProcessId())
                .timestamp(new Date())
                .build();
        ContextUtil.ctx.getBean(LogServiceClient.class).createLog(logDTO);
    }

}
