package com.std.video.geyiming.tec2;

/**
 * 守护线程：<p/>
 * 1. 守护线程和具体的业务无关。<p/>
 * 2. 开启守护线程的方法：setDaemon(true)，只能在线程开启前设定。<p/>
 * 3. The Java Virtual Machine exits when the only threads running are all daemon threads
 * 4. 在Deamon中产生的新线程也是Deamon的，即后台线程。
 * <p/>
 * <p/>
 * 由线面的例子可以看出：<p/>
 * MyBussCommon作为业务线程，是前台线程，一直在执行，直到业务执行完毕。<p/>
 * MyDaemon作为守护线程，是后台线程，当前台线程运行完毕只剩守护线程，程序退出。<p/>
 * 但是：MyDaemon的服务并没有完毕（没有循环完9999999次），由于MyCommon结束，自动结束。<p/>
 * <p/>
 * MyDaemon的结束（程序退出）依赖于MyCommon的运行完毕。
 *
 * @author zhaojy
 * @createTime 2017-04-18
 */
public class D1_DaemonDemo {

    /**
     * 线程的继承实现方式
     */
    public static class Daemon implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 业务线程
     */
    static class MyBussCommon extends Thread {
        public void run() {
            for (int i = 0; i <= 100; i++) {
                System.out.println("service - 线程,第" + i + "次执行！");
                try {
                    Thread.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 守护线程
     */
    static class MyDaemon implements Runnable {
        public void run() {
            for (long i = 0; i < 9999999L; i++) {
                System.out.println("deamon - 线程,第" + i + "次执行！");
                try {
                    Thread.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new MyBussCommon();
        Thread t2 = new Thread(new MyDaemon());
        t2.setDaemon(true);
        t1.start();
        t2.start();
    }
}
