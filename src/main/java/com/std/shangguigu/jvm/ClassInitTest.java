package com.std.shangguigu.jvm;

/**
 * 测试类
 *
 * @author zhaojy
 */
public class ClassInitTest {
    private static int num = 1;

    static {
        num = 2;

        // 可以给number 赋值
        number = 20;
        // 报错，非法的前向引用
        // System.out.println(number);
    }

    private static int number = 10;

    public static void main(String[] args) {
        System.out.println(ClassInitTest.num);
        System.out.println(ClassInitTest.number);
    }
}
