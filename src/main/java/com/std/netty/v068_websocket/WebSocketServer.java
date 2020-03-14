package com.std.netty.v068_websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * webSocket模拟长连接
 *
 * @author zhaojy
 * @date 2020/3/14 16:30
 */
public class WebSocketServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            // 基于http，使用http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());

                            // 以块的方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            /**
                             * 说明：
                             *  1. http数据在传输的过程中是分段传输，HttpObjectAggregator就可以将多个段聚合起来，
                             *  2. 这就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                             *
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /**
                             * 1. 对应webSocket，他的数据是以帧（frame）的形式传递
                             * 2. 可以看到WebSocketFrame 下面有6个子类
                             * 3. 浏览器请求时，ws://localhost:7000/xxx，表示请求的uri
                             * 4. WebSocketServerProtocolHandler 核心功能将http协议升级为ws协议，保持长连接
                             * 5. 为什么http协议能升级为webSocket协议呢？
                             *
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            // 自定义handler，处理业务逻辑
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
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
