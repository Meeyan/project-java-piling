package com.std.video.tec2;

/**
 *
 */
public class BadSuspend {
    public static Object u = new Object();  // 锁
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            this.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                Thread.currentThread().suspend();   // 挂起
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(2000);
        t2.start();
        t1.resume();
        t2.resume();
        t1.join();
        t2.join();
    }
}

