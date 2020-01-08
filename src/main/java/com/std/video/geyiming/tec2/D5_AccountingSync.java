package com.std.video.geyiming.tec2;

/**
 * 加锁讲解(3) - 锁作用于静态方法：相当于对当前类加锁，进入同步代码前要获得当前类的锁。全局共享唯一一把锁
 *
 * @author zhaojy
 * @date 2017-04-19
 */
public class D5_AccountingSync implements Runnable {
    private static int count = 0;

    /**
     * 对静态方法加锁，可以实现线程安全
     */
    public static synchronized void increase() {
        count++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000000L; i++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 此时，全局共享一把锁，业务实例不做影响
        Thread t1 = new Thread(new D5_AccountingSync());
        Thread t2 = new Thread(new D5_AccountingSync());

        t1.start();
        t2.start();

        // 让主线程等待t1，t2结束，再执行
        t1.join();
        t2.join();
        System.out.println(count);
    }
}
