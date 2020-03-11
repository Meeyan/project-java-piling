package com.std.netty.v061_byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * 测试 ByteBuf 相关方法
 *
 * @author zhaojy
 * @date 2020/3/11 22:01
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {

        Charset charset = Charset.forName("utf-8");

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", charset);
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();

            System.out.println(new String(content, charset));
            System.out.println("byteBuf=" + byteBuf);


            System.out.println(byteBuf.arrayOffset());

            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            System.out.println(byteBuf.getByte(0));

            // 可读长度
            int len = byteBuf.readableBytes();

            System.out.println("len=" + len);
            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0, 4, charset));
            System.out.println(byteBuf.getCharSequence(4, 6, charset));

        }
    }
}
