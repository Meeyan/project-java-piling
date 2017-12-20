package com.std.video.geyiming.tec5;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 死锁检查器
 *
 * @author zhaojy
 * @createTime 2017-07-27
 */
public class DeadLockChecker {

    private final static ThreadMXBean mbean = ManagementFactory.getThreadMXBean();

    final static Runnable deadLockCheck = new Runnable() {
        @Override
        public void run() {
            while (true) {

                long[] deadlockedThreads = mbean.findDeadlockedThreads();
                if (deadlockedThreads != null) {
                    // 获取死锁的线程信息
                    ThreadInfo[] threadInfos = mbean.getThreadInfo(deadlockedThreads);  // 获取死锁的线程

                    // 获取所有alive的线程。
                    for (Thread thread : Thread.getAllStackTraces().keySet()) {
                        for (ThreadInfo threadInfo : threadInfos) {
                            if (thread.getId() == threadInfo.getThreadId()) {
                                thread.interrupt();
                            }
                        }
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 检查死锁，并干掉死锁
     */
    public static void check() {
        Thread thread = new Thread(deadLockCheck);
        thread.setDaemon(true); // 设定为守护进程跑
        thread.start();
    }
}
