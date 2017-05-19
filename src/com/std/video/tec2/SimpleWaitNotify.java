package com.std.video.tec2;

/**
 * wait 和 notify实例
 *
 * @author zhaojy
 * @createTime 2017-04-28
 */
public class SimpleWaitNotify {

    // 生命一把锁，object
    final static Object object = new Object();  // 锁

    /**
     * wait方法在执行前，必须获取到锁才行，否则，会有异常，这也就意味着，wait必须放于synchronized块内。
     */
    public static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + " : T1 start.");
                try {
                    System.out.println(System.currentTimeMillis() + " : T1 wait from object.");
                    object.wait();  // 线程进入等待，释放object监视器。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(System.currentTimeMillis() + " : T1 end.");
            }
        }
    }

    /**
     * notify()执行前需要获取监视器（即锁）
     */
    public static class T2 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + " : T2 start! notify one thread!");
                object.notify();    // 唤醒等待在object监视器上的线程,释放锁
                System.out.println(System.currentTimeMillis() + " : T2 end. Monitor will be released 2s later");
                try {
                    Thread.sleep(2000); // 睡2秒后，再释放锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
