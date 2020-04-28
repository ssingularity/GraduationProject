package com.sjtu.project.common.response;

import lombok.Getter;

public enum  ResultCode {
    SUCCESS(0, "success"),
    ERROR(-1, "error"),
    OBJECT_NOT_FOUND(-2, "%s not found"),
    REPLICATE_SUBSCRIPTION(-27, "已经发起过订阅请求"),
    NON_SUBSCRIPTION(-28, "订阅请求不存在"),
    SUBSCTIPTION_STATUS_ERROR(-29, "订阅请求的状态出错");


    @Getter
    private int code;

    @Getter
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
