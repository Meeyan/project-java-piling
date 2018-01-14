package com.std.video.geyiming.tec9;

/**
 * @author zhaojy
 * @create-time 2018-01-10
 */
public class D6_IntegerLock {
    static Integer i = 0;

    static class AddThread extends Thread {
        @Override
        public void run() {
            for (int j = 0; j < 100000; j++) {
                synchronized (i) {
                    /*
                     * 解读：
                     *   对于Integer的对象做自增操作，设计到装箱和拆箱的操作。
                     *   i++后，生成一个新的对象，让i指向新的对象。
                     */
                    i++;

                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread th1 = new AddThread();
        AddThread th2 = new AddThread();
        th1.start();
        th2.start();
        th1.join();
        th2.join();

        System.out.println(i);
    }
}
