package com.std.video.geyiming.tec2;

/**
 * wait 和 notify实例
 *
 * @author zhaojy
 * @date 2017-04-28
 */
public class D6_SimpleWaitNotify {

    // 声明一把锁，object
    final static Object object = new Object();  // 锁

    /**
     * wait方法在执行前，必须获取到锁才行，否则，会有异常，这也就意味着，wait必须放于synchronized块内。
     */
    public static class T1 extends Thread {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            // (1) 获取锁
            synchronized (object) {
                System.out.println(name + " get the lock and start.");
                try {
                    System.out.println(name + " wait from object.");
                    System.out.println(Thread.currentThread().getName() + " - 开始进入等待状态，同时释放锁");
                    long startWTime = System.currentTimeMillis();
                    object.wait();  // (2) 线程进入等待，释放object监视器。
                    long endWTime = System.currentTimeMillis();
                    System.out.println(Thread.currentThread().getName() + " - 被唤醒，继续执行，等待时长：" + (endWTime - startWTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name + " end.");
            }
        }
    }

    /**
     * notify()执行前需要获取监视器（即锁）
     * 如果T2先锁，T1就会进入一直wait状态（因为没有notify）
     */
    public static class T2 extends Thread {

        @Override
        public void run() {
            // (1) 获取锁
            synchronized (object) {
                String name = Thread.currentThread().getName();
                System.out.println(name + " get lock and start! notify one thread!");
                object.notify();    // 唤醒等待在object监视器上的线程,释放锁
                System.out.println(name + ", Monitor will be released 2s later");

                try {
                    Thread.sleep(2000); // 睡2秒后，再释放锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(name + " end..");
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();

        t1.start();
        t2.start();
    }
}
