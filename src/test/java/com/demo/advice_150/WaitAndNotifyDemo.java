package com.demo.advice_150;

/**
 * @author zhaojy
 * @date 2020/10/26 3:54 下午
 */
public class WaitAndNotifyDemo {

    public static void main(String[] args) {
        MyWaitThread myThread = new MyWaitThread();

        /**
         * synchronized (myThread) ：
         * - 1. 主线程会阻塞 MyWaitThread
         * - 2. 主线程 wait 后，会释放锁，然后 myThread 运行
         * - 3. myThread notify 主线程后，继续运行，直到结束
         */
        synchronized (myThread) {
            try {
                myThread.start();
                // 主线程睡眠 3s
                Thread.sleep(3000);
                System.out.println("before wait");
                // 阻塞主线程
                myThread.wait();
                System.out.println("after wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class MyWaitThread extends Thread {
    public void run() {
        synchronized (this) {
            System.out.println("before notify");
            notify();
            System.out.println("after notify");
        }
    }
}