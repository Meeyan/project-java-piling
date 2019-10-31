package com.std.BFBCDYS.v0043;

/**
 * @author zhaojy
 * @date 2019/10/30 8:29 PM
 */
public class TestCase {
    public static void main(String[] args) {
        // 1. 上班前预订蛋糕
        Order order = ProductFactory.createProductOrder("赵晓哪的蛋糕");

        // 2. 去上班
        System.out.println("我去上班，下班可以拿蛋糕");

        // 3. 下班回家
        System.out.println("下班回来，拿着蛋糕回家。。" + order);
    }
}
