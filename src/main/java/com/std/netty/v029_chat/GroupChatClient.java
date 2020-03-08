package com.std.netty.v029_chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 群聊客户端
 *
 * @author zhaojy
 * @date 2020/3/5
 */
public class GroupChatClient {


    private final String host = "127.0.0.1";

    private final int port = 6667;

    private Selector selector;

    private SocketChannel socketChannel;

    private String userName;


    public GroupChatClient() throws Exception {

        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));

        // 非阻塞
        socketChannel.configureBlocking(false);

        // 注册channel到selector
        socketChannel.register(selector, SelectionKey.OP_READ);


        userName = socketChannel.getLocalAddress().toString().substring(1);
    }

    public void sendInfo(String info) {
        info = userName + " said:" + info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int select = selector.select();
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        // 获取channel
                        SocketChannel channel = (SocketChannel) selectionKey.channel();

                        // 分配缓冲区，todo 如果消息数量大于1024呢？
                        ByteBuffer allocate = ByteBuffer.allocate(1024);

                        // 读取数据到缓冲区
                        channel.read(allocate);

                        String msg = new String(allocate.array());
                        System.out.println(msg.trim());
                    }
                }

                // 删除当前的，防止重复操作
                iterator.remove();
            } else {
                System.out.println("没有可用的通道");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        GroupChatClient groupChatClient = new GroupChatClient();

        // 每隔3秒从server端读取数据
        new Thread(() -> {
            while (true) {
                groupChatClient.readInfo();
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        // 扫描器，发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String info = scanner.nextLine();
            groupChatClient.sendInfo(info);
        }
    }
}
