package com.std.yeziyuan.L030;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaojy
 * @date 2019-06-22 15:53
 */
public class Customer implements Runnable {

    private Tmall tmall;

    public Customer(Tmall tmall) {
        this.tmall = tmall;
    }

    @Override
    public void run() {
        while (true) {
            tmall.take();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
