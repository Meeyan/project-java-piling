package com.std.deep_understand_jvm.aiguigu.ch09;

import java.io.Serializable;

/**
 * 方法区内部结构
 *
 * @author zhaojy
 * @date 2020/9/16 4:53 下午
 */
public class MethodInnerStructureTest extends Object implements Comparable<String>, Serializable {

    public int num = 10;

    private static String str = "测试方法内部结构";

    /**
     * 方法一
     */
    public void test1() {
        int count = 20;
        System.out.println("count=" + count);
    }

    public static int test2(int cal) {
        int result = 0;
        try {
            int value = 30;
            result = value / cal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public int compareTo(String o) {
        return 0;
    }
}
