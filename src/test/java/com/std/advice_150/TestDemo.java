package com.std.advice_150;

import com.util.BinaryUtil;
import org.junit.Test;

public class TestDemo {

    @Test
    public void sayHello() {

    }

    public static void main(String[] args) {
        System.out.println(1 << 30);
        System.out.println(Integer.MAX_VALUE);
        testState();
    }

    public void testUser() {
        System.out.println(~2);
    }

    public static void testState() {
        System.out.println((3 << (Integer.SIZE - 3)));
        System.out.println(BinaryUtil.formatBinaryStr32(Integer.toBinaryString((3 << (Integer.SIZE - 3)))));
    }

}

