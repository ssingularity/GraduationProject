package com.sjtu.project.userservice.domain.nullobject;

import com.sjtu.project.userservice.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NullUser extends User {
    public NullUser(String username) {
        setUsername(username);
    }

    @Override
    public UserDetails convert2UserDetails() {
        throw new UsernameNotFoundException(getUsername());
    }
}
