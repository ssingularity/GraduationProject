package com.sjtu.project.userservice.handler;

import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import com.sjtu.project.common.response.ResultCode;
import com.sjtu.project.common.util.JWTUtil;
import com.sjtu.project.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String token = JWTUtil.generateToken(((UserDetails)authentication.getPrincipal()).getUsername(), authentication.getAuthorities());
        httpServletResponse.addHeader(HttpHeaders.AUTHORIZATION, token);
        Map<String, String> res = new HashMap<>();
        res.put("res", ResultCode.SUCCESS.getMessage());
        res.put("token", token);
        httpServletResponse.getWriter().println(JsonUtil.writeValueAsString(res));
    }
}
