package com.std.gof23.L07_proxy.dynamicProxy;

import java.lang.reflect.Proxy;

/**
 * 客户端，模拟顾客
 *
 * @author zhaojy
 * @date 2018-03-09
 */
public class Client {
    public static void main(String[] args) {
        // 此处已经拿到了歌星周杰伦，为什么还要用代理呢？应该将歌星的选择放到代理内部实现
        Star zjy = new ZhouJieLun();
        StarDynamicProxy dynamicProxy = new StarDynamicProxy(zjy);
        Star star = (Star) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Star.class}, dynamicProxy);
        star.sing();
    }
}