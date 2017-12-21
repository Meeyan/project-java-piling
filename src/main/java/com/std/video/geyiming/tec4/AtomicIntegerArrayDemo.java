package com.std.video.geyiming.tec4;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 非阻塞式同步-demo
 * 针对数组-integer使用。
 *
 * @author zhaojy
 * @createTime 2017-05-31
 */
public class AtomicIntegerArrayDemo {
    static AtomicIntegerArray integerArray = new AtomicIntegerArray(10);
    private static final long serialVersionUID = 2862133569453664235L;

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                /*
                 * 疑问：对比AtomicStampedRefDemo，该例子总是成功，而AtomicStampedRefDemo却有失败，为什么？
                 *  AtomicStampedRefDemo：持有状态值，每个线程做操作时，要依赖stamp。
                 *  AtomicIntegerArrayDemo：对数组的增长属于自增，且getAndIncrement总是成功的。
                 */
                integerArray.getAndIncrement(i % integerArray.length()); // 自增操作，入参为下标
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        List<Thread> thsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            thsList.add(new Thread(new AddThread()));
        }

        for (Thread th : thsList) {
            th.start();
        }

        for (Thread th : thsList) {
            th.join();
        }

        System.out.println(integerArray);


        /*
         * 想要获取Unsafe时请注意，因为这个对象输入非公开api的，所以内部有对加载器的安全检查。
         * 两种方式获取：
         *   1. 获取加载器,只能在特定场合下使用
         *   2. 反射，可以公开使用
         */
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);
        int base = unsafe.arrayBaseOffset(int[].class);         // 16
        int scale = unsafe.arrayIndexScale(int[].class);        // 4
        int shift = 31 - Integer.numberOfLeadingZeros(scale);   // 2

        System.out.println(base);
        System.out.println(scale);
        System.out.println(shift);

    }
}
