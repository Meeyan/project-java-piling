package com.std.netty.v092_tcpProtocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * 客户端handler
 *
 * @author zhaojy
 * @date 2020/3/21 17:43
 */
public class TcpProClientHandler extends SimpleChannelInboundHandler<TcpMessage> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端发送10条数据
        String content = "疫情要过去了，准备吃火锅";
        for (int i = 0; i < 10; i++) {
            byte[] bytes = (content + i).getBytes(StandardCharsets.UTF_8);
            TcpMessage tcpMessage = new TcpMessage();
            tcpMessage.setLength(bytes.length);
            tcpMessage.setContent(bytes);
            ctx.writeAndFlush(tcpMessage);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TcpMessage msg) throws Exception {
        System.out.println("收到服务端消息，长度" + msg.getLength() + ",内容：" + new String(msg.getContent(), StandardCharsets.UTF_8));
        System.out.println("收到服务端消息总量:" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
