package com.std.BFBCDYS.chapter005;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试公平锁和非公平锁
 *
 * @author zhaojy
 * @date 2019-07-01 19:43
 */
public class FairAndUnfairTest {

    private static ReentrantLock2 failLock = new ReentrantLock2(true);
    private static ReentrantLock2 unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {

    }

    @Test
    public void unfair() {
        Job job = new Job(failLock);
    }


    private static class Job extends Thread {
        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.println(lock.getQueuedThreads());
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        public Collection<Thread> getQueuedThreads() {
            List<Thread> list = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(list);
            return list;
        }
    }

}
