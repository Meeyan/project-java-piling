package com.std.spring.service;

import com.std.spring.annotations.GPService;

/**
 * @author zhaojy
 * @create-time 2018-03-28
 */
@GPService
public class DemoServiceImpl implements IDemoService {
    @Override
    public String getName(String name) {
        return "hello " + name;
    }
}
