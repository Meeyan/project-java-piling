package com.std.springboot.exception;

import com.std.springboot.common.bo.CommonJsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author zhaojy
 * @date 2019-05-07
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonJsonResult exceptionHandler(Exception e) {
        CommonJsonResult result = new CommonJsonResult();
        result.setCode("9999");
        result.setMessage("系统异常，请稍后重试");
        return result;
    }
}
