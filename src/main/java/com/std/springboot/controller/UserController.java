package com.std.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @RequestMapping("/info")
    public String getUserInfo() {
        return "张三";
    }
}
