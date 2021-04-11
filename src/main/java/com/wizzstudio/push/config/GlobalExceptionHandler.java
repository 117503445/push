package com.wizzstudio.push.config;


import com.wizzstudio.push.model.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResult<String> exceptionHandler(HttpServletRequest req, Exception e) {
        return new CommonResult<>(1, "err", e.getMessage());
    }
}