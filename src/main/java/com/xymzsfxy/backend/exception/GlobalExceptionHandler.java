package com.xymzsfxy.backend.exception;

import com.xymzsfxy.backend.returncode.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.badRequest(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
