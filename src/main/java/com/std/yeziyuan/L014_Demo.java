package com.std.yeziyuan;

/**
 * synchronized 关键字的解读
 *
 * @author zhaojy
 * @date 2019-06-05
 */
public class L014_Demo {

    private static int value = 0;

    /**
     * synchronized 修饰普通方法
     */
    public synchronized void getNext() {
        System.out.println(value++);
    }

    /**
     * synchronized 修饰静态方法，内置锁使用Class对象，全局唯一
     */
    public static synchronized void getNextInt() {
        System.out.println(value++);
    }


    /**
     * synchronized 修饰代码块，锁对象，可以使用Class对象或者实例好的锁对象
     */
    public static void getNextIntMethod() {
        synchronized (L014_Demo.class) {
            System.out.println(value++);
        }
    }
}
