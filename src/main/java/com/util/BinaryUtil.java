package com.util;

/**
 * @author zhaojy
 * @date 2020/2/15 12:47 AM
 */
public class BinaryUtil {

    /**
     * 格式化32为二进制
     *
     * @param str String
     * @return String
     */
    public static String formatBinaryStr32(String str) {
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

    /**
     * 格式化64为二进制[前面补0]
     *
     * @param str String
     * @return String
     */
    public static String formatBinaryStr64(String str) {
        if (str.length() < 64) {
            int pre = 64 - str.length();
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

    /**
     * 转换成二进制
     *
     * @param object Object
     * @return String
     */
    public static String toBinaryString(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Integer) {
            return Integer.toBinaryString((Integer) object);
        }
        if (object instanceof Long) {
            return Long.toBinaryString((Long) object);
        }
        if (object instanceof String) {
            char[] chars = ((String) object).toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char tmpChar : chars) {
                sb.append(Integer.toBinaryString(tmpChar)).append("\n");
            }
            return sb.toString();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(formatBinaryStr32("1111000"));
        System.out.println(toBinaryString(12819L));
    }
}
