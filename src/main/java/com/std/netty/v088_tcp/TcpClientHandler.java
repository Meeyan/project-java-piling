package com.std.netty.v088_tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * 客户端handler
 *
 * @author zhaojy
 * @date 2020/3/21 17:43
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端发送10条数据
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello, server " + i, CharsetUtil.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        System.out.println("收到服务端消息:" + new String(bytes, StandardCharsets.UTF_8));
        System.out.println("收到服务端消息总量:" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
