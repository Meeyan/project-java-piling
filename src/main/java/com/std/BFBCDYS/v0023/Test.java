package com.std.BFBCDYS.v0023;

/**
 * 测试程序
 */
public class Test {

    private int state = 0;

    private CustomerAQSLock lock = new CustomerAQSLock();

    public int getState() {
        lock.lock();
        try {
            Thread.sleep(100);
            return state++;
        } catch (Exception e) {
            throw new RuntimeException("");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " -- " + test.getState());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " -- " + test.getState());
            }
        }).start();


        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " -- " + test.getState());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " -- " + test.getState());
            }
        }).start();
    }
}
