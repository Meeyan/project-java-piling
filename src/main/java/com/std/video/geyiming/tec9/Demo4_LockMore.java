package com.std.video.geyiming.tec9;

/**
 * 锁优化
 *
 * @author zhaojy
 * @create-time 2018-01-10
 */
public class Demo4_LockMore {

    private static Object lock = new Object();

    /**
     * 锁粗化-未粗化前
     */
    public void method_lock_more_1() {
        synchronized (lock) {
            // do sth 1
        }
        // 做其他不需要同步的工作，但能很快完成
        synchronized (lock) {
            // do sth 2
        }
    }

    /**
     * 锁粗化-粗化后
     */
    public void method_lock_more_opt() {
        // 整合合成一次锁
        synchronized (lock) {
            // do sth 1
            // 做其他不需要同步的工作，但能很快完成
            // do sth 2
        }
    }

    /**
     * 锁优化-案例2
     */
    public void method_lock_more_for() {
        for (int i = 0; i < 1000; i++) {
            // 重复申请锁，浪费资源
            synchronized (lock) {
                // do sth
            }
        }
        // todo 要替换成下面的方式
        synchronized (lock) {
            for (int i = 0; i < 1000; i++) {
                // do sth
            }
        }

    }

}
