package com.std.springboot.controller;

import cn.hutool.json.JSONUtil;
import com.std.springboot.common.bo.ConfigUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 *
 * @author zhaojy
 */
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


    @RequestMapping("/testException")
    public String testException() {
        System.out.println(1 / 0);
        return "";
    }

    /**
     * 注意，此方法和'sayUserName'是两个不同的方法
     *
     * @return String
     */
    @RequestMapping("/sayHello")
    public String sayHello() {
        return "hello, welcome visit my method.";
    }

    /**
     * {userName} 必须被赋值
     *
     * @param userName String
     * @return String
     */
    @RequestMapping("/sayHello/{userName}")
    public String sayUserName(@PathVariable(value = "userName", required = false) String userName) {
        return "hello," + userName;
    }

    /**
     * 测试参数
     *
     * @param userName String
     * @param attrId   String
     * @return String
     */
    @RequestMapping("/getParams")
    public String getParams(@RequestParam("userName") String userName, @RequestAttribute("attrId") String attrId) {
        return "params:" + userName + "," + attrId;
    }

}
