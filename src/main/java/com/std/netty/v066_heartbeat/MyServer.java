package com.std.netty.v066_heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaojy
 * @date 2020/3/14 15:43
 */
public class MyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler());

                            /**
                             * 加入一个netty提供的IdleStateHandler，空闲状态处理器
                             * long readerIdleTime：表示多久没有读，就会发送一个心跳检测包检测连接是否有效。
                             * long writerIdleTime：多长时间没有写，就会发送一个心跳检测包检测连接是否有效。
                             * long allIdleTime：表示多长时间没有读和写，就会发送一个心跳检测包检测连接是否有效。
                             * TimeUnit unit：时间单位
                             *
                             * 当IdleStateEvent触发后，就会传递给管道的下一个handler去处理，通过调用（触发）下一个handler的userEventTriggered,
                             * 在该方法中去处理IdleStateEvent对应的事件
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));

                            // pipeLine 加入一个检测空闲的处理handler

                            pipeline.addLast(new MyServerHandler());

                        }
                    });

            ChannelFuture sync = serverBootstrap.bind(7000).sync();
            sync.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
