package com.demo.other;

/**
 * @author zhaojy
 * @date 2020/11/27 10:46 上午
 */
public class InitClassOrderExample {
    public static void main(String[] args) {
        Child child = new Child();
        System.out.println(child);
    }
}

class Parent {
    private static String name = "李四";

    static {
        System.out.println("parent static code");
    }

    {
        System.out.println("parent code");
    }

    private String age = "18";

    public Parent() {
        System.out.println("parent constructor code");
    }
}

class Child extends Parent {
    private static String name = "zhangsan";

    static {
        System.out.println("Child static code");
    }

    {
        System.out.println("Child code");
    }

    private String age = "18";

    public Child() {
        System.out.println("Child constructor code");
    }
}