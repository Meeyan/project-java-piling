package com.std.gof23.L01_singleton;

/**
 * 单利模式 - 懒汉式
 * 延迟加载，真正用的时候才加载【资源利用率高】
 * 但是，并发效率低【有锁】
 *
 * @author zhaojy
 * @create-time 2018-03-02
 */
public class D2_LazyLoadMode {
    private D2_LazyLoadMode() {
    }

    static D2_LazyLoadMode instance = null;

    /**
     * 懒汉式加载
     *
     * @return
     */
    public static synchronized D2_LazyLoadMode getInstance() {
        if (instance == null) {
            instance = new D2_LazyLoadMode();
        }
        return instance;
    }
}
