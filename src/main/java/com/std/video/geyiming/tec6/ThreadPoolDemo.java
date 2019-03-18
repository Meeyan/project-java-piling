package com.std.video.geyiming.tec6;

import java.util.List;
import java.util.Vector;

/**
 * 模拟线程池的实现
 *
 * @author zhaojy
 * @date 2017-12-26
 */
public class ThreadPoolDemo {
    // 线程池
    private static ThreadPoolDemo instance = null;
    private List<Worker> idleThreads;       // 空闲的线程队列
    private int threadCounter;              // 已有的线程总数
    private boolean isShutDown = false;

    private ThreadPoolDemo() {
        this.idleThreads = new Vector<>(5);
        threadCounter = 0;
    }

    public int getCreatedThreadCount() {
        return threadCounter;
    }

    public synchronized static ThreadPoolDemo getInstance() {
        if (instance == null)
            instance = new ThreadPoolDemo();
        return instance;
    }

    protected synchronized void rePool(Worker rePoolingThread) {
        if (!isShutDown) {
            idleThreads.add(rePoolingThread);
        } else {
            rePoolingThread.shutdown();
        }
    }

    /**
     * 关闭线程池
     */
    public synchronized void shutdown() {
        isShutDown = true;
        for (int threadIndex = 0; threadIndex < idleThreads.size(); threadIndex++) {
            Worker worker = idleThreads.get(threadIndex);
            worker.shutdown();
        }
    }

    /**
     * 启动线程池
     *
     * @param target Runnable
     */
    public synchronized void start(Runnable target) {
        Worker thread = null;
        // 有空闲线程
        if (idleThreads.size() > 0) {
            int lastIndex = idleThreads.size() - 1;
            thread = idleThreads.get(lastIndex);
            idleThreads.remove(lastIndex);  // 从线程池中移除
            thread.setTarget(target);
        } else {
            threadCounter++;
            thread = new Worker(target, "PThread #" + threadCounter, this);
            thread.start();
        }
    }

}
