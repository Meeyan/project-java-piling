package com.std.video.geyiming.tec6;

/**
 * @author zhaojy
 * @create-time 2017-12-26
 */
public class Worker extends Thread {

    private Runnable target;
    private ThreadPoolDemo threadPoolDemo;
    private boolean isIdle = false;
    private boolean isShutdown = false;

    public Worker(Runnable target, String name, ThreadPoolDemo threadPoolDemo) {
        super(name);
        this.target = target;
        this.threadPoolDemo = threadPoolDemo;
    }

    public void shutdown() {
        isShutdown = true;
        notifyAll();
    }

    public Runnable getTarget() {
        return target;
    }

    public boolean isIdle() {
        return isIdle;
    }

    public synchronized void setTarget(Runnable target) {
        this.target = target;
        notifyAll();    // 通知有线程进来
    }

    @Override
    public void run() {
        while (!isShutdown) {
            isIdle = false;
            if (target != null) {
                // 运行任务
                target.run();
            }

            // 任务结束
            isIdle = true;
            try {
                // 任务结束后，不关闭线程，而是放入线程池空闲队列
                threadPoolDemo.rePool(this);
                synchronized (this) {
                    // 线程空闲，等待新的任务到来
                    wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isIdle = false;
        }
    }

}