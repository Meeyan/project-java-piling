package com.std.yeziyuan;

/**
 * 线程 - 中断
 *
 * @author zhaojy
 * @date 2019-05-23
 */
public class L006_Demo {

    public static void main(String[] args) {
        testInterrupted();
    }

    public static void testInterrupted() {
        L006_Thread thread = new L006_Thread();
        thread.start();
        // 中断
        thread.interrupt();
    }
}

class L006_Thread extends Thread {
    @Override
    public void run() {
        /*
         * 1. 采用while(true)方式，如果线程被中断，会有异常产生，
         * 2. 采用interrupted()判断，则不会出现异常
         */
        while (true) {
            System.out.println(Thread.currentThread().getName() + "--runs ");
            /**
             *  优化的中断方式。实际开发中不会使用
             */
            if (interrupted()) {
                System.out.println(Thread.currentThread().getName() + "--interrupted ");
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
