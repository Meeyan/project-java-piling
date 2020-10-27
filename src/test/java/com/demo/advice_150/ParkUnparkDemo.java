package com.demo.advice_150;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zhaojy
 * @date 2020/10/26 4:02 下午
 */
public class ParkUnparkDemo {

    static class MyThread extends Thread {
        private Thread object;

        public MyThread(Thread object) {
            this.object = object;
        }

        public void run() {
            System.out.println("before unpark");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 获取blocker
            System.out.println("Blocker info " + LockSupport.getBlocker(object));
            // 释放许可
            LockSupport.unpark(object);
            // 休眠500ms，保证先执行park中的setBlocker(t, null);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 再次获取blocker
            System.out.println("Blocker info " + LockSupport.getBlocker(object));

            System.out.println("after unpark");
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread(Thread.currentThread());
        myThread.start();

        System.out.println("before park");

        // 调用 park 方法后，主线程会被禁用（暂停）
        LockSupport.park("ParkAndUnparkDemo");

        System.out.println("after park");
    }
}
