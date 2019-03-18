package com.std.jvm;

/**
 * @author zhaojy
 * @date 2018-02-24
 */
public class D3_JvmDemo {
    public static void main(String[] args) {
        byte[] b = null;
        for (int i = 0; i < 10; i++) {

            b = new byte[1024 * 1024];
        }
    }
}
