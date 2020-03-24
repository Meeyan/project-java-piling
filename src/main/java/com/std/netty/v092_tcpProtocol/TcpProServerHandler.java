package com.std.netty.v092_tcpProtocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author zhaojy
 * @date 2020/3/21 17:44
 */
public class TcpProServerHandler extends SimpleChannelInboundHandler<TcpMessage> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TcpMessage msg) throws Exception {
        System.out.println("TcpProServerHandler channelRead0 被调用");
        System.out.println("长度：" + msg.getLength() + ",内容：" + new String(msg.getContent(), StandardCharsets.UTF_8));
        System.out.println("读取次数：" + (++this.count));

        byte[] bytes = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);


        // 回复一个消息
        TcpMessage rep = new TcpMessage();
        rep.setLength(bytes.length);
        rep.setContent(bytes);

        ctx.writeAndFlush(rep);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
