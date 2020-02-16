package com.std.dataStructure.L006_recursion;

/**
 * 迷宫问题
 * 底层用算法实现，不模拟图形
 *
 * @author zhaojy
 * @date 2020/2/11 11:18 PM
 */
public class MiGong {
    public static void main(String[] args) {

        int rowLength = 8;
        int colLength = 7;
        // 一个二维数组，类似迷宫
        int[][] map = new int[rowLength][colLength];
        /**
         * 使用1标识墙，需要将四周全部置为1
         *      1 1 1 1 1 1 1
         *      1 0 0 0 0 0 1
         *      1 0 0 0 0 0 1
         *      1 0 0 0 0 0 1
         *      1 0 0 0 0 0 1
         *      1 0 0 0 0 0 1
         *      1 0 0 0 0 0 1
         *      1 1 1 1 1 1 1
         *
         */

        // 第一行和最后一行为1，以列为纬度遍历
        for (int i = 0; i < colLength; i++) {
            map[0][i] = 1;
            map[rowLength - 1][i] = 1;
        }

        // 第一列和最后一列为1，以行为纬度遍历
        for (int i = 0; i < rowLength; i++) {
            map[i][0] = 1;
            map[i][colLength - 1] = 1;
        }

        // 设置挡板
        map[3][1] = 1;
        map[3][2] = 1;

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        // 使用递归回溯，给小球找路

        setWay(map, 1, 1);

        System.out.println("小球走过并标识的地图如下：");
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }


    /**
     * 使用递归来给小球找路
     * 说明：
     * 1. map标识地图
     * 2. i,j表示从地图的哪个位置开始出发（1，1）
     * 3. 如果小球能到map[6][5]位置，则说明通路找到
     * 4. 约定：
     * 当map[i][j]为0，表示该点没有做过，
     * 为1表示墙，
     * 2表示通路可以走，
     * 3表示该点已经走过，但是走不通
     * 5. 在走迷宫时，需要确定一个策略（方法），下 -> 右 -> 上 -> 左 （如果一个点走不通，再回溯）
     *
     * @param map 地图
     * @param i   从那个位置开始着 行
     * @param j   位置 列
     * @return 找到位置
     */
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        }

        // 点尚未走过
        if (map[i][j] == 0) {
            // 按照策略：下 -> 右 -> 上 -> 左 走
            map[i][j] = 2;
            if (setWay(map, i + 1, j)) {
                // 向下走通
                return true;
            } else if (setWay(map, i, j + 1)) {
                // 向右走通
                return true;
            } else if (setWay(map, i - 1, j)) {
                /// 向上走通
                return true;
            } else if (setWay(map, i, j - 1)) {
                // 向左走通
                return true;
            } else {
                // 走到此处，该点走不通的
                map[i][j] = 3;
                return false;
            }
        } else {
            // 该点可能是1，2，3
            return false;
        }
    }
}
