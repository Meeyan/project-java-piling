package com.std.BFBCDYS.v0007;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 第七节讲解
 *
 * @author zhaojy
 * @date 2019-05-23
 */
public class L007_Demo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        methodFutureTask();

    }

    /**
     * 猜猜那种方式会运行?
     */
    static void method_1() {
        /*
         * 这种方式下，该输出什么？
         * 查看线程启动的方法得知: 重写run方法后，target方式传入也不会执行。
         *  调用start后，虚拟机将会调用run方法，但是由于已经重写了run方法，所以构造传入的target无效
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("targer method");
            }
        }) {
            @Override
            public void run() {
                System.out.println("over-write");
            }
        }.start();
    }

    /**
     * futureTask模式
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void methodFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new SubCallable());
        new Thread(task).start();

        System.out.println(Thread.currentThread().getName() + "---main thread continues");
        System.out.println("do something else.");
        System.out.println(task.get());
    }
}

/**
 * 带返回任务的多线程
 */
class SubCallable implements Callable<Integer> {

    /**
     * 真正任务的执行业务体
     *
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("我要开始处理任务了");
        Thread.sleep(2000);
        return 8912;
    }
}
