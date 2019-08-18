package com.std.BFBCDYS.v0032;

import java.util.concurrent.TimeUnit;

/**
 * 消费者任务
 *
 * @author zhaojy
 * @date 2019/8/13 23:01
 */
public class TakerTarget implements Runnable {
    private Tmall tmall;

    public TakerTarget(Tmall tmall) {
        this.tmall = tmall;
    }

    @Override
    public void run() {
        while (true) {

            tmall.take();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
