package com.std.srljjxnj.zl.v005;

/**
 * 初始化子类，会主动初始化父类
 * -XX:+TraceClassLoading : 显示加载信息
 * @author zhaojy
 * @date 2020/4/19 14:55
 */
public class InitChild {
    public static void main(String[] args) {
        System.out.println(MyChild.str2);
    }
}

class MyParent {
    public static String str = "hello static Str";

    static {
        System.out.println("MyParent static block");
    }
}

class MyChild extends MyParent {
    public static String str2 = "hello child static Str";

    static {
        System.out.println("MyChild static block");
    }
}