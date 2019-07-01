package com.std.BFBCDYS.chapter005;

import com.std.utils.SleepUtils;
import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * @author zhaojy
 * @date 2019-07-01 18:59
 */
public class TwinsLockTest {
    final Lock lock = new TwinsLock();

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }

        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }

    }


    class Worker extends Thread {

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    SleepUtils.second(1);
                    System.out.println(Thread.currentThread().getName());
                    SleepUtils.second(1);
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
