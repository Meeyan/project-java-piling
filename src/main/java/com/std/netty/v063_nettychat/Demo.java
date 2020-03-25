package com.std.netty.v063_nettychat;

/**
 * @author zhaojy
 * @date 2020/3/20 3:48 PM
 */
public class Demo {
    public static void main(String[] args) {
        ClassLoader classLoader = Demo.class.getClassLoader();
        System.out.println(classLoader);
        ClassLoader classLoaderParent = classLoader.getParent();
        System.out.println(classLoaderParent);


    }
}
