package com.Interview.thread;

/**
 * 创建多线程的方式
 *
 * @author zhaojy
 * @date 2019-05-15
 */
public class L1_CreateThread {


    public static void main(String[] args) {
        createThreadMethod_2();
    }

    /**
     * 方法一
     */
    public static void createThreadMethod_1() {
        SubThread subThread = new SubThread();
        subThread.start();
        System.out.println("main Thread finished-" + Thread.currentThread().getName());
    }

    /**
     * 方法二
     */
    public static void createThreadMethod_2() {
        // 构造线程核心业务对象
        RunnableThread runnableThread = new RunnableThread();

        // 启动线程
        new Thread(runnableThread).start();

        System.out.println("main Thread finished-" + Thread.currentThread().getName());
    }
}

/**
 * 创建线程方式一：
 * 1. 继承Thread类，重写Run方法
 * 2. 调用start启动线程
 */
class SubThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "_" + i);
        }
    }
}

/**
 * 创建线程方式二：
 * 1. 实现Runnable接口
 * 2. 重写run方法
 * 3. 将Runnable实现类对象作为Thread的target参数传入到Thread中
 * 4. 调用Thread的start方法启动线程
 */
class RunnableThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "_" + i);
        }
    }
}