package com.demo.thread.pool;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 调度线程池
 * 参考：https://www.jianshu.com/p/925dba9f5969
 *
 * @author zhaojy
 * @date 2021/1/4 1:50 下午
 */
public class ScheduledThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        // 提交调度任务
        for (int i = 0; i < 10; i++) {
            Task task = new Task("task-" + i);
            scheduledExecutorService.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        }

        Thread.sleep(10000);

        System.out.println("Shutting down executor...");

        scheduledExecutorService.shutdown();

        boolean isDone;
        do {
            isDone = scheduledExecutorService.awaitTermination(1, TimeUnit.DAYS);
        } while (!isDone);

        System.out.println("Finished all threads");
    }

}

class Task implements Runnable {

    String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("name = " + name + ", startTime = " + new Date());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("name = " + name + ", endTime = " + new Date());
    }
}