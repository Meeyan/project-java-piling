package com.std.video.geyiming.tec2;

/**
 * wait 和 notify实例 - notifyAll()
 * todo:测试发现会产生死锁
 *
 * @author zhaojy
 * @date 2017-05-17
 */
public class D7_SimpleWaitNotifyAll {

    // 生命一把锁，object
    final static Object object = new Object();  // 锁

    /**
     * wait方法在执行前，必须获取到锁才行，否则，会有异常，这也就意味着，wait必须放于synchronized块内。
     */
    public static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("T1 start! Wait on object. ");
                try {
                    // find-bugs发现的问题，
                    // Unconditional wait
                    // This method contains a call to java.lang.Object.wait() which is not guarded by conditional control flow. 
                    // The code should verify that condition it intends to wait for is not already satisfied before calling wait;
                    // any previous notifications will be ignored.
                    object.wait();          // 线程进入等待，释放object监视器。
                    Thread.sleep(1000);     // 1秒钟后，释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1 end.");
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
                System.out.println("T2 start! notify all threads!");
                object.notifyAll();
                System.out.println("T2 end. Monitor will be released 2s later");
                try {
                    Thread.sleep(2000); // 睡2秒后，再释放锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试发现，会随机导致死锁
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t11 = new T1();
        Thread t2 = new T2();

        t1.start();
        t11.start();
        t2.start();
    }
}
