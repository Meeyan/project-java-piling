package com.std.gof23.L01_singleton;

/**
 * 单例模式 - 枚举方式实现
 * 枚举本身就是单例模式。由jvm从根本上提供保障。
 * 避免通过反射和反序列化的漏洞
 * 性能也可以
 * 但是：无法延迟加载
 *
 * @author zhaojy
 * @create-time 2018-03-02
 */
public enum D5_EnumMode {
    // 枚举
    INSTANCE;

    /**
     * 单利对象的处理逻辑
     */
    public void singletonOperation() {

    }
}
