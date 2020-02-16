package com.std.dataStructure.L006_recursion;

/**
 * 递归测试
 *
 * @author zhaojy
 * @date 2020/2/10 9:59 PM
 */
public class RecursionTest {
    public static void main(String[] args) {
        // test(100);
        System.out.println(factorial(4));
    }

    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.println("n=" + n);
    }

    /**
     * 阶乘
     *
     * @param n int
     * @return
     */
    public static int factorial(int n) {
        if (n == 1) {
            return n;
        } else {
            return factorial(n - 1) * n;
        }
    }
}
