package com.std.test;

import java.util.ArrayList;

/**
 * @author zhaojy
 * @date 2018-02-27
 */
public class TestVisualVm {
    public static void main(String[] args) {
        ArrayList<byte[]> list = new ArrayList<>();
        for (int i = 0; i <  20; i++) {
            list.add(new byte[1024 * 1024]);
        }
    }
}



