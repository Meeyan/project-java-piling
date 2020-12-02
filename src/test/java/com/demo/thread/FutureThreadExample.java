package com.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * FutureTask + thread 示例
 *
 * @author zhaojy
 * @date 2020/12/2 6:22 下午
 */
public class FutureThreadExample {
    public static void main(String[] args) {

        /**
         * 第一种方式:Future + ExecutorService
         * Task task = new Task();
         * ExecutorService service = Executors.newCachedThreadPool();
         * Future<Integer> future = service.submit(task1);
         * service.shutdown();
         */


        /**
         * 第二种方式: FutureTask + ExecutorService
         * ExecutorService executor = Executors.newCachedThreadPool();
         * Task task = new Task();
         * FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
         * executor.submit(futureTask);
         * executor.shutdown();
         */

        /**
         * 第三种方式:FutureTask + Thread
         */

        FutureTask<Integer> futureTask = new FutureTask<Integer>(new DemoTask());

        Thread thread = new Thread(futureTask);
        thread.setName("Task Thread");
        thread.start();

        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");

        if (!futureTask.isDone()) {
            System.out.println("Task is not finish");
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int result = 0;
        try {
            result = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result is:" + result);

    }
}

class DemoTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

        System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");

        int result = 0;
        for (int i = 0; i < 100; ++i) {
            result += i;
        }

        Thread.sleep(3000);
        return result;
    }
}
