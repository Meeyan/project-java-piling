package com.demo.other;

/**
 * @author zhaojy
 * @date 2020/11/26 6:00 下午
 */
public class StringExample {
    public static void main(String[] args) {
        String name = "abc";
        String lisi = new String("abc").intern();
        System.out.println(name == lisi);
        short s1 = 1;
        System.out.println(s1);

        short s2 = 0;
        s2 += 1;
        s2++;
        System.out.println(s2);
    }
}
