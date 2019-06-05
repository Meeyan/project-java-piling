package com.Interview.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 创建多线程的方式
 *
 * @author zhaojy
 * @date 2019-05-15
 */
public class L1_CreateThread {


    public static void main(String[] args) {
        createThreadMethod_3();
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


    /**
     * 方法二
     */
    public static void createThreadMethod_3() {
        // 构造线程核心业务对象
        Callable<Boolean> callable = new CallThread();

        // 将核心业务方法交给futureTask处理，FutureTask重写了run方法
        FutureTask<Boolean> futureTask = new FutureTask<>(callable);

        Thread th1 = new Thread(futureTask);
        th1.start();
        
        Thread th2 = new Thread(futureTask);
        th2.start();
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


/**
 * 创建线程方式三：
 * 1. 实现Callable，重写call方法
 */
class CallThread implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "_" + i);
        }
        return true;
    }
}