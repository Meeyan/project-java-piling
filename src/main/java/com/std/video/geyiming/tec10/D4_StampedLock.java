package com.std.video.geyiming.tec10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * @author zhaojy
 * @create-time 2018-01-18
 */
public class D4_StampedLock {

    static class Point {
        private double x, y;

        // 读写锁，优化的
        private final StampedLock lock = new StampedLock();

        void move(double deltaX, double deltaY) {
            long writeStamped = lock.writeLock();
            x += deltaX;
            y += deltaY;
            lock.unlockWrite(writeStamped);
        }

        double distanceFromOrigin() {
            long optimisticRead = lock.tryOptimisticRead();
            double currentX = x, currentY = y;
            if (!lock.validate(optimisticRead)) {
                optimisticRead = lock.readLock();   // 退化成读锁
                currentX = x;
                currentY = y;
                lock.unlockRead(optimisticRead);
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }
    }

    static class CalPoint implements Runnable {

        String name;
        Point point;
        double x, y;

        public CalPoint(Point point, double x, double y, String name) {
            this.point = point;
            this.x = x;
            this.y = y;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("--" + name + " begins");
            point.move(this.x, this.y);
            System.out.println("--" + name + " get " + point.distanceFromOrigin());
        }
    }

    public static void main(String[] args) {
        Point point = new Point();
        ExecutorService esc = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            esc.execute(new CalPoint(point, (double) i, (double) i, "name-" + i));
        }
    }

}
