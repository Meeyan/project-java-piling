package com.demo.innerClass;

/**
 * @author zhaojy
 * @date 2020/11/27 10:37 上午
 */
public class OuterClassExample {
    public static void main(String[] args) {
        OuterClassExample outerClassExample = new OuterClassExample();

        // 访问静态内部类，不依赖外部类实例
        StaticInnerClass staticInnerClass = new StaticInnerClass();

        // 访问内部类，需要依赖外部类实例
        InnerClass innerClass = outerClassExample.new InnerClass();
    }

    class InnerClass {

    }

    static class StaticInnerClass {

    }
}
