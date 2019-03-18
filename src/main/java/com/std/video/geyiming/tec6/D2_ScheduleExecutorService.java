package com.std.video.geyiming.tec6;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojy
 * @date 2017-12-27
 */
public class D2_ScheduleExecutorService {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);

        /*
         * 参数讲解：
         * command - the task to execute
         * initialDelay - the time to delay first execution
         * delay - the delay between the termination of one execution and the commencement of the next
         * unit - the time unit of the initialDelay and delay parameters
         */
        ses.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis() / 1000 + " ---- " + Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
