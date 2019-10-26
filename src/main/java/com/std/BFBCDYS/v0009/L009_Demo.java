package com.std.BFBCDYS.v0009;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 使用Spring支持多线程
 *
 * @author zhaojy
 * @date 2019-05-25
 */
@Component
@EnableAsync
public class L009_Demo {

    @Async
    public void methodA() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "-methodA runs");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void methodB() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "-methodB runs");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
