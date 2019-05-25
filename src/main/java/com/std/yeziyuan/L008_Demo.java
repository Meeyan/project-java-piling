package com.std.yeziyuan;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 第八讲：使用Timer执行定时任务。可以使用Quartz框架替代
 * 缺点：不可控,对于计算耗时不可确定的任务，使用timer方式不合适。
 *
 * @author zhaojy
 * @date 2019-05-25
 */
public class L008_Demo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("我在执行定时任务..");
            }
        }, 0, 1000);
    }
}
