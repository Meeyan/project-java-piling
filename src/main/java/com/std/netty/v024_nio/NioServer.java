package com.std.netty.v024_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio socket server
 *
 * @author zhaojy
 * @date 2020/3/3
 */
public class NioServer {
    public static void main(String[] args) throws Exception {

        // 创建ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 获取Selector对象
        Selector selector = Selector.open();

        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverSocketChannel注册到Selector，事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接进来");
                continue;
            }

            // selectedKeys 返回关注事件的集合
            // select返回的大于0，表示已经获取到关注的事件
            // 通过 selectionKeys 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                // 获取对应的SelectionKey
                SelectionKey selectionKey = keyIterator.next();

                // 根据key，对应的通道发生的事件做相应的处理

                // OP_ACCEPT ,表示有新的客户端连接
                if (selectionKey.isAcceptable()) {

                    // 该客户端生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    // 因为要注册，必须设定为非阻塞
                    socketChannel.configureBlocking(false);

                    // 将当前的 socketChannel 注册到selector中
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                // 发生read事件
                if (selectionKey.isReadable()) {
                    // 通过key，反向获取到对应的channel
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    // 获取该channel关联的buffer
                    ByteBuffer readBuffer = (ByteBuffer) selectionKey.attachment();


                    // TODO: 2020/3/4    断线重连怎么做？
                    int read = socketChannel.read(readBuffer);
                    System.out.println(read + "from 客户端：" + new String(readBuffer.array()));

                }

                keyIterator.remove();
            }
        }
    }
}