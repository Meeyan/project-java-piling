package com.std.dataStructure.L001_sparsearray;

/**
 * 稀疏数组，
 * 无论怎么算，稀疏数组存储数据和直接存储原二维数组，仍旧是节省空间的
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

        // 先遍历二维数组
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    sum++;
                }
            }
        }

        // sum+1行和3列
        // sparseArr 结构 ： row col value
        int sparseArr[][] = new int[sum + 1][3];
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        // 构造稀疏数组
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
        }

        // 输出稀疏数组的结构
        for (int[] row : sparseArr) {
            for (int data : row) {
                System.out.printf("%d ", data);
            }
            System.out.println();
        }


        // 将稀疏数组恢复成原始的二维数组

        // 1. 先确定原有的数组的大小
        int chessArr2[][] = new int[sparseArr[0][0]][sparseArr[0][1]];

        // 2. 恢复稀疏数组，从第二行开始
        for (int i = 1; i < sparseArr.length; i++) {
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        System.out.println("恢复后的数据为:");
        for (int[] row : chessArr2) {
            for (int data : row) {
                System.out.printf("%d ", data);
            }
            System.out.println();
        }

        // todo，将稀疏数组保存到磁盘中，再恢复成稀疏数组。

    }
}
