package com.std.netty.v092_tcpProtocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhaojy
 * @date 2020/3/21 17:41
 */
public class TcpProClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 解码器
        pipeline.addLast(new TcpProMessageDecoder());

        // 编码器
        pipeline.addLast(new TcpProMessageEncoder());

        // 业务handler
        pipeline.addLast(new TcpProClientHandler());
    }
}
