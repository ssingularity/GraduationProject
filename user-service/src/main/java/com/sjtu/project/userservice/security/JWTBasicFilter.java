package com.sjtu.project.userservice.security;

import com.sjtu.project.userservice.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JWTBasicFilter extends BasicAuthenticationFilter {
    public JWTBasicFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        UsernamePasswordAuthenticationToken authenticationToken = null;
        if (token != null && token.startsWith("Bearer ")) {
            authenticationToken = getAuthentication(token);
        }
        //如果authenticationToken为空则在之后的filter中会失败
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try{
            Claims claims = JWTUtil.parseToken(token);
            String[] subject = claims.getSubject().split(" ");
            if (subject.length != 0) {
                String username = subject[0];
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (int i=1; i< subject.length; ++i) {
                    authorities.add(new SimpleGrantedAuthority(subject[i]));
                }
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
            return null;
        }
        catch (Exception e){
            return null;
        }
    }
}
