package com.std.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代码来源于ThreadLocal中
 * @author zhaojy
 * @create-time 2018-01-16
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

        for (int i = 0; i < 10; i++) {
            System.out.println(nextHashCode());
        }
    }
}
