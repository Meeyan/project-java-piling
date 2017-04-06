package com.std.video;

/**
 * Created by zhaojy on 2017/3/31.
 */
public class ThreadDemo1 extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("hhahaha");
        }

    }

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new RunnableImpl());
        thread.start(); // 开启线程
        Thread.sleep(2000);
        thread.interrupt(); // 终止线程
    }
}

/**
 *
 */
class RunnableImpl implements Runnable {

    /**
     * 线程开启的第二种方式
     */
    @Override
    public void run() {
        /**
         * 一般而言，线程中的内容都是存在于run内部，且无限循环的执行下去。break当前循环，即终止掉。
         */
        while (true) {
            /**
             * 比较优雅的一种终结线程的方式
             * 检查线程是否有被通知中断
             */
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("-----------线程被终结了");
                break;
            }
            // todo 逻辑方法体
            System.out.println("我一直在跑着呢");
        }
    }
}
