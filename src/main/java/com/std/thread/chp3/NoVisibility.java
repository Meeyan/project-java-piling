package com.std.thread.chp3;

/**
 * 测试可见性
 * NoVisibility.main为主线程
 * ReaderThread 为读线程
 * 根据书中的解读，这种写法有问题，但是run没出来。
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            while (!ready) {
                System.out.println("111");
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread().getName());   // main
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
