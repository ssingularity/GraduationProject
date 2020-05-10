package com.sjtu.project.gateway;

import lombok.Setter;

/**
 * @Author: ssingualrity
 * @Date: 2020/5/8 22:08
 */
public class Result {
    @Setter
    int code;

    @Override
    public String toString() {
        return "{\"code\":" + code +"}";
    }
}
