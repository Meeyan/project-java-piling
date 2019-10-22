package com.std.BFBCDYS.v0038;

import java.util.concurrent.CyclicBarrier;

/**
 * comment here
 *
 * @author zhaojy
 * @date 2019/10/13 14:05
 */
public class CyclicBarrierDemo {

    public void meeting(CyclicBarrier cyclicBarrier) {
        System.out.println(Thread.currentThread().getName() + " 到达会议室，准备开会..");
        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 会议中，准备发言..");
    }

    public static void main(String[] args) {
        // 初始化10个
        CyclicBarrier meetingStartE = new CyclicBarrier(10, () -> {
            System.out.println("人到齐了，我们开始开会...");
        });

        // 类似会议室
        CyclicBarrierDemo meetingRoom = new CyclicBarrierDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                meetingRoom.meeting(meetingStartE);
            }).start();
        }
    }
}
