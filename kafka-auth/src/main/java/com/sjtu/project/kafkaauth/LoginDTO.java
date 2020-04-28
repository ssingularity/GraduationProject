package com.sjtu.project.kafkaauth;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/26 13:44
 */
@Data
@Builder
public class LoginDTO {
    private String userId;

    private String password;
}
