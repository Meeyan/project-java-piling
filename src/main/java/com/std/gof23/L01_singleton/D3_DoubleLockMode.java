package com.std.gof23.L01_singleton;

/**
 * 单例模式 - 双重检查锁实现【只有第一次同步】
 * 由于编译器优化原因和jvm底层内部模型原因，偶尔会出问题，不建议使用。
 *
 * @author zhaojy
 * @date 2018-03-02
 */
public class D3_DoubleLockMode {
    private D3_DoubleLockMode() {
    }

    private static D3_DoubleLockMode instance;

    public static D3_DoubleLockMode getInstance() {
        if (instance == null) {
            D3_DoubleLockMode sc;
            synchronized (D3_DoubleLockMode.class) {
                sc = instance;
                if (sc == null) {
                    synchronized (D3_DoubleLockMode.class) {
                        if (sc == null) {
                            sc = new D3_DoubleLockMode();
                        }
                    }
                    instance = sc;
                }
            }
        }
        return instance;
    }
}
