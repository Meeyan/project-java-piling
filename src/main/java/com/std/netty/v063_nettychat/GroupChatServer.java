package com.std.netty.v063_nettychat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;

/**
 * @author zhaojy
 * @date 2020/3/11 22:10
 */
@Data
public class GroupChatServer {
    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    public void run() {
        NioEventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);

        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossEventLoopGroup, workerLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            // 解码器
                            ch.pipeline().addLast("decoder", new StringDecoder());

                            // 编码器
                            ch.pipeline().addLast("encoder", new StringEncoder());

                            // 加入自己的业务处理handler
                            ch.pipeline().addLast(new GroupChatServerHander());
                        }
                    });

            System.out.println("netty 服务端启动完成");

            System.out.println("groupChatServer start port:" + port);
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossEventLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new GroupChatServer(7000).run();
    }
}
