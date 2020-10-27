package com.demo.advice_150.exm_002;

import java.util.Random;

/**
 * 改善Java程序的151个建议
 *
 * @author zhaojy
 * @date 2019-02-20
 */
public class MainDemo {
    /**
     * 错误写法，常量值不固定
     */
    public static final int RAND_CONST = new Random().nextInt();

    public static void main(String[] args) {
        System.out.println(RAND_CONST);
    }
}
