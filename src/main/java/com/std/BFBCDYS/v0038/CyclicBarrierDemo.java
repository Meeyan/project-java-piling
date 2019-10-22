package com.std.BFBCDYS.v0038;

import java.util.concurrent.CyclicBarrier;

/**
 * comment here
 *
 * @author zhaojy
 * @date 2019/10/13 14:05
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // 初始化10个
        CyclicBarrier meetingStartE = new CyclicBarrier(10, () -> {
            System.out.println("人到齐了，我们开始开会...");
        });
    }
}
