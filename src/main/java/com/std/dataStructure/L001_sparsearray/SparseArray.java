package com.std.dataStructure.L001_sparsearray;

/**
 * 稀疏数组
 *
 * @author zhaojy
 * @date 2020/1/8 6:42 PM
 */
public class SparseArray {
    public static void main(String[] args) {
        /**
         * 创建一个原始的二维数组 11 * 11
         * 0：没有棋子
         * 1：黑子
         * 2：白子
         */

        int chessArr[][] = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        System.out.println("原始数组如下：");
        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d ", data);
            }
            System.out.println();
        }


    }
}
