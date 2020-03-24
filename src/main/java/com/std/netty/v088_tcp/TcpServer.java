package com.std.netty.v088_tcp;

import com.std.netty.v080_inOutBound.BoundServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * tcp 粘包和拆包 服务端
 *
 * @author zhaojy
 * @date 2020/3/21 22:42
 */
public class TcpServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TcpServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
