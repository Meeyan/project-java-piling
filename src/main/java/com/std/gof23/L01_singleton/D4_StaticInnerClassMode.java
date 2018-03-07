package com.std.gof23.L01_singleton;

/**
 * 单利模式：静态内部类方式实现
 * 兼顾延迟加载和并发性能，线程安全
 *
 * @author zhaojy
 * @create-time 2018-03-02
 */
public class D4_StaticInnerClassMode {
    private D4_StaticInnerClassMode() {
    }

    // 内部类私有
    private static class StaticInnerMode {
        // 类的加载过程是线程安全的。
        private static final D4_StaticInnerClassMode instance = new D4_StaticInnerClassMode();
    }

    /**
     * 只有在调用StaticInnerMode方法时，才会加载实例
     *
     * @return
     */
    public static D4_StaticInnerClassMode getInstance() {
        return StaticInnerMode.instance;
    }
}
