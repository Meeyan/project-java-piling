package com.std.srljjxnj.zl.v007;

import java.util.UUID;

/**
 * 不能在编译期确定的常量，那么其值不会被放到调用类的常量池中，会触发常量所在类的初始化
 * @author zhaojy
 * @date 2020/4/19 16:58
 */
public class FinalUUID {
    public static void main(String[] args) {
        System.out.println(MyParent.str);
    }
}

class MyParent {

    /**
     * 调用final类型，因为值无法在编译器确定，所以会触发类的静态初始化
     */
    public static final String str = UUID.randomUUID().toString();

    static {
        System.out.println("MyParent static block");
    }
}
