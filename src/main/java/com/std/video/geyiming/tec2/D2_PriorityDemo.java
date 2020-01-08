package com.std.video.geyiming.tec2;

/**
 * 线程优先级demo
 * 优先级高，更有可能获得执行资源。
 *
 * @author zhaojy
 * @date 2017-04-19
 */
public class D2_PriorityDemo {

    public static class HighPriority extends Thread {
        private int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (D2_PriorityDemo.class) {
                    count++;
                    if (count > 9999999L) {
                        System.out.println("HighPriority is finished ");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread {
        private int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (D2_PriorityDemo.class) { // 相同的锁
                    count++;
                    if (count > 9999999L) {
                        System.out.println("LowPriority is finished ");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread high = new HighPriority();
        Thread low = new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        high.start();
        low.start();
    }
}
