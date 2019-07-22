package com;

import org.junit.Test;

public class HexDemo {
    public static void main(String[] args) {
        System.out.println(System.nanoTime());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test() {
        System.out.println(0x7fffffff);


        String abc = "adasdfa";
        int hash = abc.hashCode();
        System.out.println(hash);
        System.out.println(formatBinaryString(Integer.toBinaryString(hash)));

        int rightMove = hash >>> 16;
        System.out.println(rightMove);
        System.out.println(formatBinaryString(Integer.toBinaryString(rightMove)));

        int hR = hash ^ rightMove;
        System.out.println(formatBinaryString(Integer.toBinaryString(hR)));

        System.out.println(formatBinaryString(Integer.toBinaryString(0x7fffffff)));

        int finalR = hR & 0x7fffffff;
        System.out.println(formatBinaryString(Integer.toBinaryString(finalR)));
        System.out.println(finalR);
        System.out.println(spread(hash));
    }

    static final int spread(int h) {
        /**
         * h ^ (h >>> 16) : 只能做到高位向低位移动，但改变不了正负
         * & HASH_BITS : 其结果总是正数
         */
        return (h ^ (h >>> 16)) & 0x7fffffff;
    }


    /**
     * 格式化32为二进制
     *
     * @param str
     * @return
     */
    public static String formatBinaryString(String str) {
        if (str.length() < 32) {
            int pre = 32 - str.length();
            for (int i = 0; i < pre; i++) {
                str = "0" + str;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (i != 0 && i % 4 == 0) {
                sb.append(" ");
            }
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
