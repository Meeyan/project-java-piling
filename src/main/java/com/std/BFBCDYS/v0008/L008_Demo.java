package com.std.BFBCDYS.v0008;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 第八讲 : 使用 Timer执行定时任务，或者可以使用Quartz框架替代。
 * 缺点：不可控，对于计算耗时不确定的任务，使用定时任务难以精确控制
 *
 * @author zhaojy
 * @date 2019-05-25
 */
public class L008_Demo {
    public static void main(String[] args) {
        threadPool();
    }

    public static void timerThread() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("我在执行定时任务..");
            }
        }, 0, 1000);
    }

    /**
     * 使用线程池方式创建线程
     */
    public static void threadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        /**
         * 即使提交100个线程任务，也是由线程池创建时指定的线程数处理
         */
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Thread() {
                @Override
                public void run() {
                    System.out.println(currentThread().getName() + "-使用线程池方式运行线程..");
                }
            });
        }
        executorService.shutdown();
    }

}
