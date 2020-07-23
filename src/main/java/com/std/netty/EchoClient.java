package com.std.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;

import java.net.InetSocketAddress;

/**
 * 200221 - 完成netty 长连接 客户端基础功能，后续安排：断线重连功能
 *
 * @author zhaojy
 * @date 2020/2/21 2:27 PM
 */
@Data
public class EchoClient {
    private final String host;
    private final int port;


    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Channel channel;

    public void start() throws Exception {

        /**
         * Netty用于接收客户端请求的线程池职责如下。
         * （1）接收客户端TCP连接，初始化Channel参数；
         * （2）将链路状态变更事件通知给ChannelPipeline
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }

                    });

            //绑定端口
            ChannelFuture f = b.connect().addListener(new ConnectionListener(this)).sync();

            channel = f.channel();

            channel.closeFuture().sync();

        } catch (Exception e) {
            group.shutdownGracefully().sync();
        }
    }

    /**
     * 运行流程：
     *
     * @param args String[]
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new Thread() {
            @Override
            public void run() {
                EchoClient echoClient = new EchoClient("172.28.20.15", 10086);
                try {
                    echoClient.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
