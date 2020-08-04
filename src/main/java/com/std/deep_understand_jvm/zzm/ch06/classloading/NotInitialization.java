package com.std.deep_understand_jvm.zzm.ch06.classloading;

import org.junit.Test;

/**
 * @author zhaojy
 * @date 2020/8/3 11:24 上午
 */
public class NotInitialization {
    public static void main(String[] args) {

    }

    /**
     * 只调用父类的字段，导致父类会初始化，但是子类不会被初始化
     */
    @Test
    public void subClassNotInit() {
        System.out.println(SubClass.value);
    }

    @Test
    public void arrayNotInitialization() {
        SuperClass[] sca = new SuperClass[10];
        System.out.println(sca.length);
    }

    /**
     * 调用常量不会导致其初始化
     */
    @Test
    public void constNotInitialization() {
        System.out.println(ConstClass.HELLO_WORLD);
    }
}
