package com.std.gof23.L07_proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理模式：明星代理人
 *
 * @author zhaojy
 * @create-time 2018-03-08
 */
public class StarDynamicProxy implements InvocationHandler {

    private Star star;

    public StarDynamicProxy(Star star) {
        this.star = star;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(star, args);
        return invoke;
    }
}
