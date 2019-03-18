package com.std.video.geyiming.tec6;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 计算累加【任务分解】
 *
 * @author zhaojy
 * @date 2017-12-27
 */
public class D5_CountTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10000;
    private long start;
    private long end;

    public D5_CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCompute = (end - start) < THRESHOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 分解任务,100个
            long step = (end + start) / 100;

            ArrayList<D5_CountTask> taskList = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;  // 结束位置
                if (lastOne > end)
                    lastOne = end;
                D5_CountTask subTask = new D5_CountTask(pos, lastOne);
                pos += step + 1;    // 下一次的开始位置
                taskList.add(subTask);
                subTask.fork();
            }

            for (D5_CountTask task : taskList) {
                sum += task.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        D5_CountTask countTask = new D5_CountTask(0, 20000L);
        ForkJoinTask<Long> submit = forkJoinPool.submit(countTask);
        try {
            Long aLong = submit.get();
            System.out.println("sum:" + aLong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() {
        long sum = 0;
        for (long i = 0; i <= 20000L; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
