package com.std.gof23.L01_singleton;

/**
 * 单利-饿汉式，不用加锁。
 * 由于类初始化时就创建了对象，所以无需锁，提高性能。
 *
 * @author zhaojy
 * @create-time 2018-03-02
 */
public class D1_HungryMode {
    private D1_HungryMode() {
    }

    static D1_HungryMode instance = new D1_HungryMode();

    /**
     * 无需加锁
     * @return Object
     */
    public static D1_HungryMode getInstance() {
        return instance;
    }
}
