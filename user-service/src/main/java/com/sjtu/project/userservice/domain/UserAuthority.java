package com.sjtu.project.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class UserAuthority implements GrantedAuthority {
    UserAuthorityRole role = UserAuthorityRole.USER;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return role.name();
    }
}
