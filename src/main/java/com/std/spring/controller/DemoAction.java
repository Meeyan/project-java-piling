package com.std.spring.controller;

import com.std.spring.annotations.GPAutowired;
import com.std.spring.annotations.GPController;
import com.std.spring.annotations.GPRequestMapping;
import com.std.spring.annotations.GPRequestParam;
import com.std.spring.service.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaojy
 * @date 2018-03-28
 */
@GPController
@GPRequestMapping("/spring")
public class DemoAction {
    @GPAutowired
    private IDemoService iDemoService;


    @GPRequestMapping("/query.json")
    public void query(HttpServletRequest request, HttpServletResponse response, @GPRequestParam String name) {

    }

    @GPRequestMapping("/edit.json")
    public void edit(HttpServletRequest request, HttpServletResponse response, @GPRequestParam String name) {

    }

}
