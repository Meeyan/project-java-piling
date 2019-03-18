package com.std.gof23.L01_singleton;

/**
 *
 * @author zhaojy
 * @date 2018-03-02
 */
public class Test {
    public static void main(String[] args) {
        D5_EnumMode instance_1 = D5_EnumMode.INSTANCE;
        D5_EnumMode instance_2 = D5_EnumMode.INSTANCE;
        if (instance_1 == instance_2) {
            System.out.println(true);
        }
    }
}
