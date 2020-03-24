package com.std.netty.v092_tcpProtocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhaojy
 * @date 2020/3/21 17:41
 */
public class TcpProServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 解码器
        ch.pipeline().addLast(new TcpProMessageDecoder());

        // 编码器
        ch.pipeline().addLast(new TcpProMessageEncoder());

        // 业务处理
        ch.pipeline().addLast(new TcpProServerHandler());
    }
}
