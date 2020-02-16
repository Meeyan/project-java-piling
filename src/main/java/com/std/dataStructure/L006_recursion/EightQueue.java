package com.std.dataStructure.L006_recursion;

/**
 * 8皇后问题
 *
 * @author zhaojy
 * @date 2020/2/16 5:53 PM
 */
public class EightQueue {

    // 皇后的个数
    int max = 8;

    static int count = 0;

    // 保存皇后放置位置，如：arr = {0,4,7,5,2,6,1,3}，每一次保存一次。
    int[] array = new int[max];

    public static void main(String[] args) {
        EightQueue queue = new EightQueue();
        queue.check(0);
        System.out.println("总解法：" + count);
    }


    /**
     * 放置第n个皇后
     *
     * @param n int
     */
    public void check(int n) {

        // n == max时，表示max个皇后已然放好
        if (n == max) {
            print();
            return;
        }

        for (int i = 0; i < max; i++) {

            // n表示第n+1行，i表示第i+1列，从第n+1行的第一列开始，逐次检查
            array[n] = i;

            // 判断当放置第n个皇后到第i列时，是否冲突
            if (judge(n)) {

                // 接着方第n+1个皇后，即开始递归
                check(n + 1);
            }

            // 如果冲突，就继续执行array[n]=i,即将第n个皇后放置在本行的后移的一个位置（第2列，第3列...）
        }
    }

    private void print() {
        count++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    /**
     * 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突
     *
     * @param n 第n个皇后
     * @return boolean
     */
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            /**
             * 1、 array[i] == array[n]: 同一列判断
             * 2、Math.abs(n - 1) == Math.abs(array[n] - array[i])：表示判断第n个皇后是否和第i个皇后在同一斜线。
             *     Math.abs(n - 1) == Math.abs(array[n] - array[i]) 【todo 这个公示怎么得来的？？】
             *     表示纵坐标之差和横坐标之差相同，即在一个斜线上
             * 3、判断是否在同一行，没有必要，n每次都在递增。
             */
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }


}
