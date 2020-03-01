package com.std.netty.v009_buffer;

import java.nio.IntBuffer;

/**
 * buffer基本操作
 * 可以debug跟踪流程
 *
 * @author zhaojy
 * @date 2020/2/27 12:52 PM
 */
public class BasicBuffer {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < 5; i++) {
            intBuffer.put(i * 2);
        }

        // 切换读写模式
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }

}
