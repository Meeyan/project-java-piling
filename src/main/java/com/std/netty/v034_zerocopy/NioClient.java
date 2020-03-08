package com.std.netty.v034_zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * nio 零拷贝 客户端
 * 测试发现，无论服务端是否适用nio模式，客户端适用 传统IO 和 NIO零拷贝模式，性能差距很大。
 *
 * @author zhaojy
 * @date 2020/3/7
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress(7001));

        String fileName = "C:\\Users\\zhaojy\\Desktop\\Microsoft+Visual+Studio+Pro+2019.zip";

        FileInputStream fileInputStream = new FileInputStream(fileName);
        FileChannel channel = fileInputStream.getChannel();

        // 准备发送
        long startTime = System.currentTimeMillis();

        // 在linux下，一个transferTo方法就可以完成传输
        // 再windows下，一次调用transferTo方法只能发送8M，大文件需要分段传输，记录位置
        // transferTo 底层适用到零拷贝
        long transferCount = channel.transferTo(0, channel.size(), socketChannel);

        System.out.println("发送的总字节数：" + transferCount + ", 耗时：" + (System.currentTimeMillis() - startTime));
    }
}
