package com.std.BFBCDYS.chapter004.threadpool;

/**
 * 模拟线程池
 *
 * @author zhaojy
 * @date 2019-06-22 14:01
 */
public interface ThreadPool<Job extends Runnable> {

    void execute(Job job);

    void shutdown();

    void addWorkers(int num);

    void removeWorker(int num);

    int getJobSize();

}
