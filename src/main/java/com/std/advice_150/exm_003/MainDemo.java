package com.std.advice_150.exm_003;

/**
 * 改善Java程序的151个建议
 *
 * @author zhaojy
 * @date 2019-02-20
 */
public class MainDemo {
    public static void main(String[] args) {
        int i = 80;
        String s = String.valueOf(i < 100 ? 90 : 100);

        // 出现了类型转换
        String s1 = String.valueOf(i < 100 ? 90 : 100.0);
        System.out.println("两者是否相等:" + s.equals(s1));
    }
}
