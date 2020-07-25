package com.std.deeperUnderstandJVM.zzm.ch02;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 模拟直接内存oom
 * vm args : -Xmx20M -XX:MaxDirectMemorySize=10M
 *
 * @author zhaojy
 * @date 2020/4/19 13:16
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
