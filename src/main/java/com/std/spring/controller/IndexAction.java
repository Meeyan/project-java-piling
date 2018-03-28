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
 * @create-time 2018-03-28
 */
@GPController
@GPRequestMapping("/spring")
public class IndexAction {
    @GPAutowired
    private IDemoService iDemoService;


    @GPRequestMapping("/index.json")
    public void index(HttpServletRequest request, HttpServletResponse response, @GPRequestParam String name) {

    }

    @GPRequestMapping("/show.json")
    public void show(HttpServletRequest request, HttpServletResponse response, @GPRequestParam String name) {

    }

}
