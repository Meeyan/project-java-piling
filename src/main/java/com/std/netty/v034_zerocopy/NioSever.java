package com.std.netty.v034_zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * nio 零拷贝 服务端
 *
 * @author zhaojy
 * @date 2020/3/7
 */
public class NioSever {
    public static void main(String[] args) throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket socket = serverSocketChannel.socket();

        socket.bind(inetSocketAddress);

        ByteBuffer allocate = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel accept = serverSocketChannel.accept();

            int readCount = 0;
            while (-1 != readCount) {

                try {
                    readCount = accept.read(allocate);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }

                // 倒带
                allocate.rewind();
            }
        }
    }
}
