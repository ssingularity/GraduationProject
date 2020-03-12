package com.sjtu.project.userservice.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;


@Document
@Data
public class User {
    @Id
    String id = "";

    String username = "";

    String phoneNumber = "";

    String password = "";

    String description = "";

    List<UserAuthority> authorities = new ArrayList<>();

    public UserDetails convert2UserDetails(){

        return org.springframework.security.core.userdetails.User.builder().username(username)
                .password(password)
                .authorities(authorities)
                .build();
    }
}
