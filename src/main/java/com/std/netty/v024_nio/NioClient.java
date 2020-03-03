package com.std.netty.v024_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * nio socket client
 *
 * @author zhaojy
 * @date 2020/3/4
 */
public class NioClient {

    public static void main(String[] args) throws Exception {

        // 获取网络通道
        SocketChannel socketChannel = SocketChannel.open();

        // 非阻塞
        socketChannel.configureBlocking(false);

        // 提供服务端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接需要时间，客户端不会阻塞，可以做其他的工作");
            }
        }

        String str = "hello nio server";

        // 构造客户端buffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

        socketChannel.write(byteBuffer);

        System.out.println("数据发送完毕");

        System.in.read();
    }
}
