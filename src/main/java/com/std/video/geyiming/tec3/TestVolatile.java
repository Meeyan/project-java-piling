package com.std.video.geyiming.tec3;

/**
 *
 * @author zhaojy
 * @createTime 2017-05-19
 */
public class TestVolatile implements Runnable {
    private volatile int i = 0;  // 不加volatile时，两个线程的执行结果会不相同，加了volatile也不行，加锁可以

    private final static Object lock = new Object();

    @Override
    public void run() {
        synchronized (lock) {
            int c = 0;
            while (c < 100) {
                i++;
                c++;
            }
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        TestVolatile testVolatile = new TestVolatile();
        Thread t1 = new Thread(testVolatile);
        Thread t2 = new Thread(testVolatile);
        t2.start();
        t1.start();
    }
}
