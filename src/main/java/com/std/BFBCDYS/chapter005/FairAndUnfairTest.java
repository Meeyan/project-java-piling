package com.std.BFBCDYS.chapter005;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试公平锁和非公平锁
 * 观察结果：
 * 1. 公平锁总是按照一定的顺序执行的。
 * 2. 非公平锁存在任意性
 * 问题：
 * AQS中的源码「acquireQueued」方法内部，明确了只有predecessor节点和head节点相同是才会触发tryAcquire方法，
 * 如此以来，即便非公平锁，也有排队现象。
 * 解答：
 * 1. 对于非公平锁，新进来的线程，只要判断state为0，就会尝试加锁，如果加锁成功，则会绕过队列实现。
 * 2. 对于公平锁，即使新加入的节点，也会判断是否有等待节点，来决定是否抢占锁
 *
 * @author zhaojy
 * @date 2019-07-01 19:43
 */
public class FairAndUnfairTest {

    private static ReentrantLock2 fairLock = new ReentrantLock2(true);
    private static ReentrantLock2 unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {
        for (int i = 0; i < 5; i++) {
            new Job(fairLock).start();
        }
    }

    @Test
    public void unfair() {
        for (int i = 0; i < 5; i++) {
            new Job(unfairLock).start();
        }
    }

    private void testLock(ReentrantLock2 lock) {
        for (int i = 0; i < 5; i++) {
            new Job(lock).start();
        }
    }


    private static class Job extends Thread {
        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            Collection<Thread> queuedThreads = lock.getQueuedThreads();
            Long lockedId = null;
            if (lock.getOwner() != null) {
                lockedId = lock.getOwner().getId();
            }
            StringBuilder sb = new StringBuilder();
            if (!CollectionUtils.isEmpty(queuedThreads)) {
                for (Thread thread : queuedThreads) {
                    sb.append(thread.getId()).append(",");
                }
            }
            System.out.println("lock by [" + lockedId + "]----waiting ids [" + sb.toString() + "]");

            lock.lock();

            queuedThreads = lock.getQueuedThreads();
            if (lock.getOwner() != null) {
                lockedId = lock.getOwner().getId();
            }
            sb = new StringBuilder();
            if (!CollectionUtils.isEmpty(queuedThreads)) {
                for (Thread thread : queuedThreads) {
                    sb.append(thread.getId()).append(",");
                }
            }
            System.out.println("lock by [" + lockedId + "]----waiting ids [" + sb.toString() + "]");

            lock.unlock();
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

        public Thread getOwner() {
            return super.getOwner();
        }
    }

    public static void main(String[] args) {
        FairAndUnfairTest test = new FairAndUnfairTest();
        test.fair();
    }


}