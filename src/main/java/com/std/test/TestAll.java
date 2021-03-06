package com.std.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代码来源于ThreadLocal中
 *
 * @author zhaojy
 * @date 2018-01-16
 */
public class TestAll {
    private static final int HASH_INCREMENT = 0x61c88647;

    private static AtomicInteger nextHashCode = new AtomicInteger();

    /**
     * Returns the next hash code.
     */
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }


    public static void main(String[] args) {

        System.out.println(0x61c88647); // ThreadLocal中使用的16进制

        System.out.println("65535的二进制：" + Integer.toBinaryString(65535));

        System.out.println(0x7fffffff);

        for (int i = 0; i < 10; i++) {
            // System.out.println(nextHashCode());
        }
    }
}
