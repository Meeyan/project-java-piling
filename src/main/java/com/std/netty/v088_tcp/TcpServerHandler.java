package com.std.netty.v088_tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author zhaojy
 * @date 2020/3/21 17:44
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        String s = new String(bytes, Charset.forName("utf-8"));
        System.out.println("服务器接收的数据：" + s);
        System.out.println("服务器接收的消息量=" + (++this.count));

        // 服务器会送给客户端一个数据，一个随机id
        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
