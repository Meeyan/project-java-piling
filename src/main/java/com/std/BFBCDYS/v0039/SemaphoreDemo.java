package com.std.BFBCDYS.v0039;

import java.util.concurrent.Semaphore;

/**
 * 演示，资源有限，限制线程数量
 *
 * @author zhaojy
 * @date 2019/10/26 11:06 AM
 */
public class SemaphoreDemo {

    private static final int MAX_AVAILABLE = 10;

    private final Semaphore semaphore = new Semaphore(MAX_AVAILABLE);

    public PoolDemo getPoolDemo() throws InterruptedException {
        semaphore.acquire();
        return getNextAvailableItem();
    }

    public void putPoolDemo(PoolDemo poolDemo) {
        if (markAsUnused(poolDemo)) {
            semaphore.release();
        }
    }


    protected static PoolDemo[] poolDemosArr = new PoolDemo[MAX_AVAILABLE];

    static {
        // init array
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            poolDemosArr[i] = new PoolDemo();
        }
    }


    private boolean[] used = new boolean[MAX_AVAILABLE];

    protected synchronized PoolDemo getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (!used[i]) {
                used[i] = true;
                return poolDemosArr[i];
            }
        }
        return null;
    }


    protected synchronized boolean markAsUnused(PoolDemo poolDemo) {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (poolDemo == poolDemosArr[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        SemaphoreDemo demo = new SemaphoreDemo();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                PoolDemo poolDemo;
                while (true) {
                    if ((poolDemo = demo.getNextAvailableItem()) != null) {
                        System.out.println(Thread.currentThread().getName() + "------" + poolDemo.getI());
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        demo.putPoolDemo(poolDemo);
                        break;
                    }
                }
            }).start();
        }
    }
}


class PoolDemo {
    public Long i = System.nanoTime();

    public Long getI() {
        return i;
    }

    public void setI(Long i) {
        this.i = i;
    }
}