package com.std.BFBCDYS.v0042;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * futureDemo
 * 可以获取返回值的
 * 1. Callable和Runnable的区别
 * 2. 源码见src_red下
 *
 * @author zhaojy
 * @date 2019/10/26 3:42 PM
 */
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // lambda
        Callable<Integer> callable = () -> {
            System.out.println("开始执行一些任务....");
            Thread.sleep(3000);
            System.out.println("任务即将处理完毕....");
            return new Random(20).nextInt();
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        System.out.println("我去干点别的去");
        System.out.println(futureTask.get());
    }
}
