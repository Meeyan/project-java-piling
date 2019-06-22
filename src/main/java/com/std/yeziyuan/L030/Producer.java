package com.std.yeziyuan.L030;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaojy
 * @date 2019-06-22 15:53
 */
public class Producer implements Runnable {

    private Tmall tmall;

    public Producer(Tmall tmall) {
        this.tmall = tmall;
    }

    @Override
    public void run() {
        while (true) {
            tmall.push();
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
