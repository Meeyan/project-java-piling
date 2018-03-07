package com.std.jvm;

/**
 * 元类型（基本数据类型）和对象
 *
 * @author zhaojy
 * @create-time 2018-02-18
 */
public class D1_MetaTypeAndObject {

    public static void main(String[] args) {
        // 基本变量的赋值，修改，每一次对基本变量的赋值，都是直接拷贝了一份内存数据，相互不影响
        int i1 = 8;
        int i2 = i1;
        i2 = 9;
        System.out.println(i1);
        System.out.println(i2);

        // 对象数据类型的变量修改，由于都是引用的同一个内存地址，所以，相互之间的修改会有影响，这也就是线程安全的核心问题。
        ObjectVal v1 = new ObjectVal();
        v1.val = 8;
        ObjectVal v2 = v1;
        v2.val = 18;
        System.out.println(v1.val);
        System.out.println(v2.val);

        System.out.println(0x80000000);
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));

    }

    static class ObjectVal {
        private int val;
    }

    /**
     * 局部变量表 - 静态方法
     * @param i
     * @param l
     * @param f
     * @param o
     * @param b
     * @return
     */
    public static int runStatic(int i, long l, float f, Object o, byte b) {
        return 0;
    }

    /**
     * 局部变量表 - 实例方法
     * @param c
     * @param s
     * @param b
     * @return
     */
    public int runInstance(char c, short s, boolean b) {
        return 0;
    }

}
