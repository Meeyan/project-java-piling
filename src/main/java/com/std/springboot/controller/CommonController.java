package com.std.springboot.controller;

import com.std.springboot.common.bo.CommonJsonResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用控制器
 *
 * @author zhaojy
 * @date 2019-05-07
 */
@RestController
public class CommonController implements ErrorController {

    /**
     * 针对请求的路径不存在的情况进行处理
     *
     * @return CommonJsonResult
     */
    @RequestMapping("/error")
    public CommonJsonResult error() {
        CommonJsonResult result = new CommonJsonResult();
        result.setCode("9998");
        result.setMessage("请求路径不存在");
        return result;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
