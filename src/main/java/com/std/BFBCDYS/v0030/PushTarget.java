package com.std.BFBCDYS.v0030;

import java.util.concurrent.TimeUnit;

/**
 * 生产任务
 *
 * @author zhaojy
 * @date 2019/8/13 22:59
 */
public class PushTarget implements Runnable {

    private Tmall tmall;

    public PushTarget(Tmall tmall) {
        this.tmall = tmall;
    }

    @Override
    public void run() {
        while (true) {
            tmall.push();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
