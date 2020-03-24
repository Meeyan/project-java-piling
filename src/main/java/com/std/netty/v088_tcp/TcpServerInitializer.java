package com.std.netty.v088_tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhaojy
 * @date 2020/3/21 17:41
 */
public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new TcpServerHandler());
    }
}
