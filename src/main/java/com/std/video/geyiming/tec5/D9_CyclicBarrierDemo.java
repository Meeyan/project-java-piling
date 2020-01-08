package com.std.video.geyiming.tec5;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 * @author zhaojy
 * @date 2017-12-25
 */
public class D9_CyclicBarrierDemo {
    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclicBarrier;

        public Soldier(String soldier, CyclicBarrier cyclicBarrier) {
            this.soldier = soldier;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
                /*
                 * 两次循环栅栏：
                 *   第一次：等待士兵集合完成
                 *   第二次：完成任务后
                 */
            try {
                cyclicBarrier.await();  // 等待所有士兵到齐

                doWork();

                cyclicBarrier.await();  // 等待所有士兵完成任务
            } catch (InterruptedException | BrokenBarrierException e) {
                // todo 为什么会有多个异常？
                // 因为如果某个线程被interrupt后，其余处于await的线程将毫无意义，会抛BrokenBarrierException
                // 而被中断的线程会抛出InterruptedException
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(soldier + ": 任务完成..");
        }
    }

    public static class BarrierRun implements Runnable {

        boolean flag;
        int n;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            this.n = n;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令：[士兵" + n + "个，任务完成]");
            } else {
                System.out.println("司令：[士兵" + n + "个，集合完成]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int n = 10;
        Thread[] soldiers = new Thread[n];

        boolean flag = false;
        CyclicBarrier cyclicB = new CyclicBarrier(n, new BarrierRun(flag, n));
        System.out.println("集合队伍..");

        for (int i = 0; i < n; i++) {
            System.out.println("士兵：" + i + " 报道");
            soldiers[i] = new Thread(new Soldier("士兵" + i, cyclicB));
            soldiers[i].start();
//            if (i == 5) {
//                soldiers[0].interrupt();
//            }
        }
    }

}
