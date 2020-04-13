package com.sjtu.project.common.exception;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.response.ResultCode;
import com.sjtu.project.common.util.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler()
    @ResponseBody
    ResponseEntity handle(ObjectNotFoundException e) {
        return generateFailResponseEntity(ResultCode.OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler()
    @ResponseBody
    ResponseEntity handle(ServiceException e) {
        Result result = ResultUtil.failure(e.resultCode.getMessage(), e.resultCode.getCode());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    private ResponseEntity generateFailResponseEntity(ResultCode resultCode, HttpStatus ok, String... message) {
        String unformattedMessage = resultCode.getMessage();
        int code = resultCode.getCode();
        Result result = ResultUtil.failure(String.format(unformattedMessage, message), code);
        return new ResponseEntity(result, ok);
    }

}
