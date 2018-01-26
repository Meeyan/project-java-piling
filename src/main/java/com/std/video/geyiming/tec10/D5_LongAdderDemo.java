package com.std.video.geyiming.tec10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * 演示使用LongAdder【自己编写】
 *
 * @author zhaojy
 * @create-time 2018-01-19
 */
public class D5_LongAdderDemo {
    static class ThreadCounter extends Thread {
        LongAdder longAdder;
        String name;
        int initValue;

        public ThreadCounter(String name, int initValue, LongAdder adder) {
            this.name = name;
            this.initValue = initValue;
            this.longAdder = adder;
        }

        @Override
        public void run() {
            for (int i = 1; i <= initValue * 10; i++) {
                System.out.println(name + " -- " + i);
                longAdder.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LongAdder adder = new LongAdder();

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new ThreadCounter("th-" + i, i, adder);
            list.add(thread);
        }

        for (Thread th : list) {
            th.start();
            th.join();
        }
        System.out.println(adder.sum());
    }

}

