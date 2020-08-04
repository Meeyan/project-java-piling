package com.std.deep_understand_jvm.zzm.ch06.classloading;

/**
 * @author zhaojy
 * @date 2020/8/3 11:22 上午
 */
public class SuperClass {

    static {
        System.out.println("SuperClass static code init");
    }

    {
        System.out.println("SuperClass default code init");
    }


    public static int value = 123;
}


