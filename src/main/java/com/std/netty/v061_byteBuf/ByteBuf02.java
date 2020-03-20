package com.std.netty.v061_byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * ByteBuf 测试2
 *
 * @author zhaojy
 * @date 2020/3/10 1:30 PM
 */
public class ByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,World!", CharsetUtil.UTF_8);
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            System.out.println(new String(content, Charset.forName("utf-8")));
        }
    }
}
