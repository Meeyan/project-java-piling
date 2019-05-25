package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * <p>
 * example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 * @author zhaojy
 * @date 2019-05-21
 */
public class L001_TwoSum {

    public static void main(String[] args) throws Exception {
        int[] arr = {2, 8, 9, 10, 3, 3, 29, 2989, 2890, 28};
        int target = 13;
        long start = System.currentTimeMillis();
        Integer[] integers = method_1(arr, target);
        System.out.println("cost:" + (System.currentTimeMillis() - start) + " ms");
        System.out.println(integers);
    }

    /**
     * 双层遍历
     * 时间复杂度：o(n^2)
     * 空间复杂度：o(1)
     *
     * @param array  int[] 数组元素
     * @param target int 目标值
     * @return
     */
    public static Integer[] method_1(int[] array, int target) throws Exception {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] == target - array[i]) {
                    return new Integer[]{array[i], array[j]};
                }
            }
        }
        throw new Exception("not found");
    }

    /**
     * 双层遍历
     *
     * @param array  int[] 数组元素
     * @param target int 目标值
     * @return
     */
    public static Integer[] method_2(int[] array, int target) throws Exception {
        Map<Integer, Integer> keyMap = new HashMap<>(array.length);
        for (int index = 0; index < array.length; index++) {
            keyMap.put(array[index], index);
        }

        for (int i = 0; i < array.length; i++) {
            int tempValue = target - array[i];
            if (keyMap.containsKey(tempValue) && keyMap.get(tempValue) != i) {
                return new Integer[]{tempValue, array[i]};
            }
        }
        throw new Exception("not found");
    }
}
