package com.sjtu.project.userservice.handler;

import com.sjtu.project.common.response.ResultCode;
import com.sjtu.project.common.util.JsonUtil;
import com.sjtu.project.userservice.util.JWTUtil;
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

@Component
@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        String token = JWTUtil.generateToken(username, authentication.getAuthorities());
        Map<String, Object> res = new HashMap<>();
        res.put("code", ResultCode.SUCCESS.getCode());
        res.put("token", token);
        res.put("username", username);
        httpServletResponse.getWriter().println(JsonUtil.writeValueAsString(res));
    }
}
