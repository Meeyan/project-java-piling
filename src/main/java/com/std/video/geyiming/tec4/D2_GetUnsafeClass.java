package com.std.video.geyiming.tec4;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 获取Unsafe的方法
 *
 * @author zhaojy
 * @date 2018-02-02
 */
public class D2_GetUnsafeClass {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        /*
         * 想要获取Unsafe时请注意，因为这个对象输入非公开api的，所以内部有对加载器的安全检查。
         * 两种方式获取：
         *   1. 获取加载器,只能在特定场合下使用
         *   2. 反射，可以公开使用
         */
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);
        int base = unsafe.arrayBaseOffset(int[].class);         // 16
        int scale = unsafe.arrayIndexScale(int[].class);        // 4
        int shift = 31 - Integer.numberOfLeadingZeros(scale);   // 2

        System.out.println(base);
        System.out.println(scale);
        System.out.println(shift);

    }
}
