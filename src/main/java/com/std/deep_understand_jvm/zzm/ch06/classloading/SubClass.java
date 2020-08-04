package com.std.deep_understand_jvm.zzm.ch06.classloading;

/**
 * @author zhaojy
 * @date 2020/8/3 11:23 上午
 */
public class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init");
    }
}
