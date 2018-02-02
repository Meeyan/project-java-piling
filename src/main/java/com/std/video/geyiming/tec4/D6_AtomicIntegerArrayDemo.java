package com.std.video.geyiming.tec4;

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
public class D6_AtomicIntegerArrayDemo {
    static AtomicIntegerArray integerArray = new AtomicIntegerArray(10);
    private static final long serialVersionUID = 2862133569453664235L;

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                /*
                 * 疑问：
                 * 对比AtomicStampedRefDemo，该例子总是成功，而AtomicStampedRefDemo却有失败，为什么？
                 *  D5_AtomicStampedRefDemo：持有状态值，每个线程做操作时，要依赖stamp。
                 *  D6_AtomicIntegerArrayDemo：对数组的增长属于自增，且getAndIncrement总是成功的。
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

    }
}
