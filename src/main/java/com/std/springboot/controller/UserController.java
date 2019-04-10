package com.std.springboot.controller;

import cn.hutool.json.JSONUtil;
import com.std.springboot.common.bo.ConfigUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    ConfigUser configUser;

    @RequestMapping("/info")
    public String getUserInfo() {
        return "张三";
    }

    @RequestMapping("/getCfgUserInfo")
    public String getCfgUserInfo() {
        return JSONUtil.toJsonStr(configUser);
    }
}
