package com.sjtu.project.userservice.handler;

import com.google.common.io.CharStreams;
import com.sjtu.project.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Map<String, String> res = new HashMap<>();
        res.put("res", e.getMessage());
        log.info("{}", JsonUtil.writeValueAsString(res));
        httpServletResponse.getWriter().println(JsonUtil.writeValueAsString(res));
    }
}
