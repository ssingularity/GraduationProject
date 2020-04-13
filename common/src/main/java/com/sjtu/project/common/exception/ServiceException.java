package com.sjtu.project.common.exception;

import com.sjtu.project.common.response.ResultCode;
import lombok.Data;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/13 18:13
 */
@Data
public class ServiceException extends RuntimeException {
    ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
