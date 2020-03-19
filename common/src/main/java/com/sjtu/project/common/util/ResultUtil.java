package com.sjtu.project.common.util;


import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.response.ResultCode;

import static com.sjtu.project.common.response.ResultCode.SUCCESS;

public class ResultUtil {
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setData(data);
        result.setMessage(SUCCESS.getMessage());
        result.setCode(SUCCESS.getCode());
        return result;
    }

    public static Result success() {
        Result result = new Result();
        result.setMessage("");
        result.setMessage(SUCCESS.getMessage());
        result.setCode(SUCCESS.getCode());
        return result;
    }

    public static <T> Result<T> failure(String message, int code) {
        Result<T> result = new Result<>();
        result.setMessage(message);
        result.setCode(code);
        return result;
    }

    public static <T> Result<T> failure(ResultCode resultCode) {
        return failure(resultCode.getMessage(), resultCode.getCode());
    }
}
