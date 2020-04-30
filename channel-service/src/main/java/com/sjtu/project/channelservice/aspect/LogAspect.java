package com.sjtu.project.channelservice.aspect;

import com.sjtu.project.channelservice.domain.InputChannel;
import com.sjtu.project.common.domain.Message;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/30 21:53
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    LogServiceClient logServiceClient;

    @Pointcut("execution(public * com.sjtu.project.channelservice.domain.InputChannel.onMessage())")
    public void dataSourceInvoke() {}

    @Pointcut("execution(public * com.sjtu.project.channelservice.domain.InputChannel.doDispatch())")
    public void serviceInvoke() {}

    @Before("dataSourceInvoke()")
    public void dataSourceLog(JoinPoint joinPoint) {
        InputChannel inputChannel = (InputChannel) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        Message message = (Message) args[0];
        LogDTO logDTO = LogDTO.builder()
                            .type("DataSource")
                            .content(message.getContent())
                            .processId(inputChannel.getProcessId())
                            .datasourceId(message.getDatasourceId())
                            .build();
        logServiceClient.createLog(logDTO);
    }

    @Before("serviceInvoke()")
    public void serviceLog(JoinPoint joinPoint) {
        InputChannel inputChannel = (InputChannel) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        String content = (String) args[0];
        LogDTO logDTO = LogDTO.builder()
                .type("Service")
                .content(content)
                .processId(inputChannel.getProcessId())
                .serviceId(inputChannel.getTargetServiceId())
                .build();
        logServiceClient.createLog(logDTO);
    }

}
