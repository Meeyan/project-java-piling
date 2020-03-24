package com.std.netty.v092_tcpProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * tcp 粘包和拆包 客户端
 *
 * @author zhaojy
 * @date 2020/3/21 22:48
 */
public class TcpProClient {
    public static void main(String[] args) {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new TcpProClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 7000);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            eventLoopGroup.shutdownGracefully();
        }
    }
}
