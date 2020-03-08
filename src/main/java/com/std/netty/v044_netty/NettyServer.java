package com.std.netty.v044_netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * netty server
 *
 * @author zhaojy
 * @date 2020/3/8
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        // 两个线程组

        // bossGroup只是处理连接请求，真正和客户端业务处理，交给workerGroup
        /**
         * 问题：
         *  1. bossGroup 和 workerGroup含有的子线程数？
         *    默认机器cpu * 2 ，假设cpu4个，则bossGroup 8个 ，workerGroup 8个，循环使用
         */
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(boosGroup, workerGroup)
                // 使用 NioServerSocketChannel 作为服务端的通道实现
                .channel(NioServerSocketChannel.class)

                // 线程队列得到的连接个数
                .option(ChannelOption.SO_BACKLOG, 128)

                // 保持活动连接状态
                .childOption(ChannelOption.SO_KEEPALIVE, true)

                // 创建一个通道测试对象？？（匿名对象）
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {

                        // 此处可以使用一个集合管理各个channel，主动向客户端推送消息时，可以使用对应的channel，
                        // 再使用 taskQueue或者 scheduleTask完成

                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });

        // 绑定端口
        ChannelFuture channelFuture = bootstrap.bind(6668).sync();


        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("监听端口 6668 成功");
                } else {
                    System.out.println("监听端口 6668 失败");
                }
            }
        });

        // 对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();

        System.out.println("线程启动完成");
    }
}
