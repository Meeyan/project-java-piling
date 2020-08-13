package com.std.deep_understand_jvm.zzm.ch08;

import org.junit.Test;

/**
 * 局部变量表测试
 *
 * @author zhaojy
 * @date 2020/8/6 3:18 下午
 */
public class LocalVariablesTest {

    public static void main(String[] args) {
        byte[] bytes = new byte[64 * 1024 * 1024];
        System.gc();
    }

    @Test
    public void test() {
        {
            byte[] bytes = new byte[64 * 1024 * 1024];
            bytes = null;
        }
        // 不加 int a = 0; 则 gc 不会被触发
        int a = 0;
        // 但不能显式使用 GC，频繁的 GC 会降低系统的吞吐量
        System.gc();

        anyTest();
    }


    /**
     * 使用 -verbose:gc 显示 gc 信息
     */
    @Test
    public void anyTest() {
        {
            byte[] bytes = new byte[64 * 1024 * 1024];
        }
        // 不加 int a = 0; 则 gc 不会被触发
        int a = 0;
        // 但不能显式使用 GC，频繁的 GC 会降低系统的吞吐量
        System.gc();
    }

    @Test
    public void testMethodInvoke9() {
        Parent.method();
        Child.method();
    }
}

class Parent {
    static void method() {
        System.out.println("say Parent hello");
    }
}

class Child extends Parent {

    static void method() {
        System.out.println("say Child hello");
    }
}