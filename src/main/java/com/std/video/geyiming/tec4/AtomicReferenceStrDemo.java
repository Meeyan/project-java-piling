package com.std.video.geyiming.tec4;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用AtomicReference对一个字符串做线程安全的修改操作【无锁模式】
 *
 * @author zhaojy
 * @createTime 2017-12-21
 */
public class AtomicReferenceStrDemo extends Thread {
    static AtomicReference<String> atomStr = new AtomicReference<>("abc");

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        if (atomStr.compareAndSet("abc", "default")) {
            System.out.println("----" + Thread.currentThread().getId() + " modified success ");
        } else {
            System.out.println("----" + Thread.currentThread().getId() + " modified failed ");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new AtomicReferenceStrDemo().start();
        }
    }
}
