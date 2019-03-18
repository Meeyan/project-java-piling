package com.std.video.geyiming.tec10;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock锁demo示例
 * 参考：http://ifeve.com/jdk8%E4%B8%ADstampedlock%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6/
 *
 * @author zhaojy
 * @date 2018-01-18
 */
public class D4_StampedLock {

    /**
     * 例子来源于官方的api
     */
    static class Point {
        private double x, y;

        // 读写锁，优化的
        private final StampedLock lock = new StampedLock();

        /**
         * 写锁：排它锁
         * 改变坐标的位置
         *
         * @param deltaX double
         * @param deltaY double
         */
        void move(double deltaX, double deltaY) {
            long writeStamped = lock.writeLock();   // 获取写锁【1】
            try {
                x += deltaX;
                y += deltaY;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlockWrite(writeStamped); // 释放写锁【2】
            }
        }

        /**
         * 乐观读锁<br/>
         * 计算当前位置到原点的距离<br />
         * todo: 乐观读锁的标准使用步骤
         *
         * @return null
         */
        double distanceFromOrigin() {
            // （1）乐观读：没有cas操作
            long optimisticRead = lock.tryOptimisticRead();  //

            System.out.println(Thread.currentThread().getName() + " 获取到乐观读锁");

            // （2）拷贝一份值到栈里
            double currentX = x, currentY = y;  //

            // （3） 再次检查锁，如果被占用，内部获取读锁
            if (!lock.validate(optimisticRead)) {
                // （4）如果锁被占用，则获取读锁，退化成读锁：悲观锁
                optimisticRead = lock.readLock();
                try {
                    // （5）将全部变量拷贝到方法体栈内
                    currentX = x;
                    currentY = y;
                } finally {
                    // （6）释放共享读锁
                    lock.unlockRead(optimisticRead);
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " validate success");
            }
            // （7）如果锁没有被占用（或者已经被修改，使用新值），则直接返回结果
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        /**
         * 悲观锁，尝试转换锁
         *
         * @param newX
         * @param newY
         */
        void moveIfAtOrigin(double newX, double newY) {
            // （1） 先获取读锁，这里可以使用乐观锁替代
            long stamp = lock.readLock();
            try {
                // （2） 在原点附近溜达
                while (x == 0.00 && y == 0.00) {
                    // （3） 尝试将读锁升级为写锁，准备修改数据
                    long newWLock = lock.tryConvertToWriteLock(stamp);
                    if (newWLock != 0L) { // 获取锁成功
                        stamp = newWLock;
                        x = newX;
                        y = newY;
                        break;
                    } else {
                        // （5）读锁升级写锁失败，释放锁。显式获取写锁，循环重试
                        lock.unlockRead(stamp);
                        stamp = lock.writeLock();
                    }
                }
            } finally {
                // （6）释放锁
                lock.unlock(stamp);
            }
        }
    }

    static class ModifyPoint implements Runnable {

        String name;
        Point point;
        double x, y;

        public ModifyPoint(String name, Point point, double x, double y) {
            this.name = name;
            this.point = point;
            this.x = x;
            this.y = y;
        }

        @Override
        public void run() {
            System.out.println("--" + name + " begins");
            point.move(x, y);
            System.out.println("--" + name + " ends ");
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
            double v = point.distanceFromOrigin();
            System.out.println("cal value:" + name + "---" + v);
            System.out.println("--" + name + " ends ");
        }
    }

    public static void main(String[] args) {
        Point point = new Point();
        ExecutorService esc = Executors.newFixedThreadPool(2);
        double i = 20;
        esc.execute(new CalPoint(point, i, i, "reader-" + i));  // 先读，中间sleep,reader-1

        i += 5;
        esc.execute(new CalPoint(point, i, i, "reader-" + i));  // 先读，中间sleep,reader-2

        i = i * 1.5;
        esc.execute(new ModifyPoint("writer-" + 2, point, i, i));
    }

    @Test
    public void testLeftMove() {
        System.out.println(1L << 7L);   // 右移
        System.out.println(127 | 128);  // 按位或运算
        System.out.println(Long.toBinaryString(127));   // 二进制
        System.out.println(Integer.toBinaryString(-128));
        System.out.println(256 & -128);
        System.out.println(~127);

    }

}
