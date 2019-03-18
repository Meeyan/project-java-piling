package com.std.video.geyiming.tec10;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁jstack演示
 *
 * @author zhaojy
 * @date 2018-01-18
 */
public class D2_DeadLocker extends Thread {
    protected Object myDirect;
    // 四把锁
    static ReentrantLock south = new ReentrantLock();
    static ReentrantLock north = new ReentrantLock();
    static ReentrantLock west = new ReentrantLock();
    static ReentrantLock east = new ReentrantLock();

    public D2_DeadLocker(Object myDirect) {
        this.myDirect = myDirect;
        if (myDirect == south) {
            this.setName("south");
        }

        if (myDirect == north) {
            this.setName("north");
        }

        if (myDirect == west) {
            this.setName("west");
        }

        if (myDirect == east) {
            this.setName("east");
        }
    }

    @Override
    public void run() {
        // 由西朝南
        if (myDirect == south) {
            try {
                west.lockInterruptibly();
                Thread.sleep(500);
                south.lockInterruptibly();

                System.out.println("car to south has passed..");
            } catch (InterruptedException e) {
                System.out.println("car to south is killed..");
                e.printStackTrace();
            } finally {
                if (west.isHeldByCurrentThread()) {
                    west.unlock();
                }
                if (south.isHeldByCurrentThread()) {
                    south.unlock();
                }
            }
        }

        // 由东朝北
        if (myDirect == north) {
            try {
                east.lockInterruptibly();
                Thread.sleep(500);
                north.lockInterruptibly();
                System.out.println("car to north has passed..");
            } catch (InterruptedException e) {
                System.out.println("car to north is killed..");
                e.printStackTrace();
            } finally {
                if (north.isHeldByCurrentThread()) {
                    north.unlock();
                }
                if (east.isHeldByCurrentThread()) {
                    east.unlock();
                }
            }
        }

        // 由北朝西
        if (myDirect == west) {
            try {
                north.lockInterruptibly();
                Thread.sleep(500);
                west.lockInterruptibly();
                System.out.println("car to west has passed..");
            } catch (InterruptedException e) {
                System.out.println("car to west is killed..");
                e.printStackTrace();
            } finally {
                if (north.isHeldByCurrentThread()) {
                    north.unlock();
                }
                if (west.isHeldByCurrentThread()) {
                    west.unlock();
                }
            }
        }

        // 由南朝东
        if (myDirect == east) {
            try {
                south.lockInterruptibly();
                Thread.sleep(500);
                east.lockInterruptibly();
                System.out.println("car to east has passed..");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("car to east is killed..");
            } finally {
                if (south.isHeldByCurrentThread()) {
                    south.unlock();
                }
                if (east.isHeldByCurrentThread()) {
                    east.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        D2_DeadLocker car2South = new D2_DeadLocker(south);
        D2_DeadLocker car2North = new D2_DeadLocker(north);
        D2_DeadLocker car2West = new D2_DeadLocker(west);
        D2_DeadLocker car2East = new D2_DeadLocker(east);

        car2South.start();
        car2North.start();
        car2West.start();
        car2East.start();
        Thread.sleep(20000);
    }

}
