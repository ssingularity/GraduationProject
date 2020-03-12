package com.sjtu.project.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTUtil {
    public static String key = "MyJwtSecret";

    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }

    public static String generateToken(String username, Collection<? extends GrantedAuthority> grantedAuthorities) {
        List<String> subject = new ArrayList<>();
        subject.add(username);
        grantedAuthorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(subject::add);
        return "Bearer " + Jwts.builder()
                .setSubject(String.join(" ", subject))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
