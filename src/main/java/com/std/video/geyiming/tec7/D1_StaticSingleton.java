package com.std.video.geyiming.tec7;

/**
 * 无锁的懒汉式单例模式
 *
 * @author zhaojy
 * @create-time 2017-12-27
 */
public class D1_StaticSingleton {

    static int count = 1;

    /**
     * 构造函数私有
     */
    public D1_StaticSingleton() {
        System.out.println(" 什么都不做 !");
    }

    /**
     * 类的初始化放到内部类中处理。
     */
    private static class SingletonHolder {
        private static D1_StaticSingleton instance = new D1_StaticSingleton();
    }

    public static D1_StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }

}
