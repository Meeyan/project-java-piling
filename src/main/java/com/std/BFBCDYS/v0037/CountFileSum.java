package com.std.BFBCDYS.v0037;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 的使用
 * 给CountDownLatch一个初始数（大于0），等CountDownLatch等于0时，标识任务完成
 *
 * @author zhaojy
 * @date 2019/9/6 23:29
 */
public class CountFileSum {
    private int[] sums;

    public CountFileSum(int line) {
        this.sums = new int[line];
    }

    public void calc(String line, int index, CountDownLatch latch) {
        String[] split = line.split(",");

        int total = 0;
        for (String num : split) {
            total += Integer.parseInt(num);
        }

        sums[index] = total;
        System.out.println(Thread.currentThread().getName() + " 执行计算任务...." + line + " 结果为：" + total);
        latch.countDown();
    }

    public void sum() {
        System.out.println("汇总线程开始执行....");
        int total = 0;
        for (int tmpNum : sums) {
            total += tmpNum;
        }
        System.out.println("最终结果为:" + total);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> allLines = Files.readAllLines(Paths.get("E:/num-file.txt"));
        int lineCount = allLines.size();
        CountDownLatch latch = new CountDownLatch(lineCount);

        CountFileSum fileSum = new CountFileSum(lineCount);

        for (int i = 0; i < lineCount; i++) {
            final int j = i;
            new Thread(() -> {
                fileSum.calc(allLines.get(j), j, latch);
            }).start();
        }

        latch.await();

        fileSum.sum();

    }
}
