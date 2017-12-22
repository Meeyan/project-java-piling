package com.std.video.geyiming.tec4;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 非阻塞式，针对对象同步的操作
 *
 * @author zhaojy
 * @createTime 2017-05-27
 */
public class AtomicReferenceDemo {

    // 操作字符换
    static AtomicReference<String> atomicStr = new AtomicReference<>("abc");

    public static void main(String[] args) throws InterruptedException {

        // 测试字符串
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Math.abs((int) (Math.random() * 100)));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (atomicStr.compareAndSet("abc", "def")) {
                        System.out.println(Thread.currentThread().getId() + " changed value succeed ");
                    } else {
                        System.out.println(Thread.currentThread().getId() + " changed value failed ");
                    }
                }
            }.start();
        }
    }
}
