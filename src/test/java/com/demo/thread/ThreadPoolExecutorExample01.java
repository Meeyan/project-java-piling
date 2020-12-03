package com.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadPoolExecutor 示例1
 *
 * @author zhaojy
 * @date 2020/12/3 11:10 上午
 */
public class ThreadPoolExecutorExample01 {
    public static void main(String[] args) {
        // 5 个固定的线程，负责执行提交的任务，线程复用
        /**
         * 使用线程池的好处：
         * 1、不用频繁的创建和销毁线程，节省系统资源
         * 2、提高响应速度（无须创建线程）
         * 3、提高线程的可管理性（线程过多是否要拒绝，异常情况，是否有返回值等）
         */
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new WorkThread("" + i));
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {

        }

        System.out.println("Finish all threads");
    }
}

class WorkThread implements Runnable {

    private String command;

    public WorkThread(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start. command :" + command);
        processCommand();
        System.out.println(Thread.currentThread().getName() + " end");
    }

    private void processCommand() {
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "WorkThread{" +
                "command='" + command + '\'' +
                '}';
    }
}
