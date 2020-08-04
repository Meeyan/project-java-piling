package com.std.deep_understand_jvm.zzm.ch06.classloading;

/**
 * 常量类初始化测试
 *
 * @author zhaojy
 * @date 2020/8/3 2:08 下午
 */
public class ConstClass {
    static {
        System.out.println("ConstClass static init!");
    }

    /**
     * 仅仅调用 该常量，并不会导致 ConstClass的初始化，原因是因为常量在编译期已经确定其值了。
     */
    public static final String HELLO_WORLD = "hello world!";
}
