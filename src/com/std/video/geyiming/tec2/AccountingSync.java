package com.std.video.geyiming.tec2;

/**
 * 加锁讲解(1) - 指定加锁对象
 * 两个线程共享一个锁instance
 *
 * @author zhaojy
 * @createTime 2017-04-19
 */
public class AccountingSync implements Runnable {

    // 如果锁不是static的呢？锁 实例
    private static final AccountingSync instance = new AccountingSync();

    private static int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
            synchronized (instance) {
                count++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 由于线程共享既定的锁(instance)，无论实例是否相同，结果都是一样的
        Thread t1 = new Thread(new AccountingSync());
        Thread t2 = new Thread(new AccountingSync());
        t1.start();
        t2.start();

        // 等待两个线程结束
        t1.join();
        t2.join();
        System.out.println(count);
    }
}
