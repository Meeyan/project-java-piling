package com.demo.other;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaojy
 * @date 2020/10/29 11:53 上午
 */
public class DemoTest {
    public static void main(String[] args) {
        System.out.println(1 << 9);

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>(21);
        for (int i = 0; i < 50; i++) {
            String key = new Random().nextInt(1000) + "";
            String value = System.nanoTime() + "";
            concurrentHashMap.put(key, value);
        }

        System.out.println(concurrentHashMap);
    }

    /**
     * 根据传入的数字，计算出大于等于值c的最小2的n次方数
     * 5 -> 8           2^3
     * 9,10,11.. -> 16  2^4
     * <p>
     * Returns a power of two table size for the given desired capacity.
     * See Hackers Delight, sec 3.2
     */
    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;

        // 当 n 小于等于 4 时，右移 2 位，高位将全部为 0 ，所以 |= 操作后，n 保持原值
        n |= n >>> 2;

        // 当 n 小于等于 15 时，右移 4 位，高位将全部为 0 ，所以 |= 操作后，n 保持原值
        n |= n >>> 4;

        // 当 n 小于等于 255 时，右移 8 位，高位将全部为 0 ，所以 |= 操作后，n 保持原值
        n |= n >>> 8;

        // 当 n 小于等于 65535 时，右移 16 位，高位将全部为 0 ，所以 |= 操作后，n 保持原值
        n |= n >>> 16;

        return (n < 0) ? 1 : (n >= 1000000) ? 1000000 : n + 1;
    }

    @Test
    public void testTableSize() {
        System.out.println(tableSizeFor(300));
    }

    @Test
    public void testEqual() {
        Object t = new Object();
        System.out.println(t == (t = new Object()));
    }
}
