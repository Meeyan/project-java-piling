package com.std.netty.v029_chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author zhaojy
 * @date 2020/3/5
 */
public class GroupChatServer {
    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private int port = 6667;

    public void start() {
        try {
            // 获取选择器
            selector = Selector.open();

            // 获取channel
            serverSocketChannel = ServerSocketChannel.open();

            // 绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(port));

            // 非阻塞
            serverSocketChannel.configureBlocking(false);

            // 注册 accept事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {

        try {
            while (true) {

                // 超时2秒
                int count = selector.select();

                // 有事件需要处理
                if (count > 0) {

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        // 处理连接事件
                        if (key.isAcceptable()) {
                            SocketChannel accept = serverSocketChannel.accept();

                            // 设置非阻塞
                            accept.configureBlocking(false);

                            // 注册到selector，关注read事件
                            accept.register(selector, SelectionKey.OP_READ);

                            System.out.println(accept.getRemoteAddress() + " 上线了");
                        }

                        // 通道发生读操作
                        if (key.isReadable()) {
                            groupChat(key);
                        }

                        // 移除，防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("server waiting");
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 群消息转发
     *
     * @param selectionKey
     * @throws IOException
     */
    private void groupChat(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) selectionKey.channel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);

            // 读取到了数据
            if (count > 0) {
                String message = new String(byteBuffer.array(), 0, count);
                System.out.println("from 客户端:" + message);

                // 向其他客户端转发消息，排除自己
                sendInfoToOtherClients(message, channel);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (channel != null) {
                System.out.println(channel.getRemoteAddress() + "离线了");

                // 取消注册
                selectionKey.cancel();

                // 关闭通道
                channel.close();
            }
        }
    }

    /**
     * 转发消息 - 转发时要排除自己
     *
     * @param msg         String
     * @param selfChannel SocketChannel 代表当前自己的channel，需要被排除
     */
    private void sendInfoToOtherClients(String msg, SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息。。。");

        // 所有注册到selector上的 socketChannel，并排除self
        for (SelectionKey key : selector.keys()) {
            SelectableChannel targetChannel = key.channel();

            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != selfChannel) {
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());


                // 向channel中写入数据
                ((SocketChannel) targetChannel).write(wrap);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.start();
        chatServer.listen();
    }
}
