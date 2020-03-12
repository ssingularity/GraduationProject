package com.sjtu.project.common.response;

import lombok.Getter;

public enum  ResultCode {
    SUCCESS(0, "success"),
    ERROR(-1, "error"),
    OBJECT_NOT_FOUND(-2, "%s not found");


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
