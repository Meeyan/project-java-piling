package com.std.video.geyiming.tec8;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于NIO优化的socket
 *
 * @author zhaojy
 * @date 2018-01-03
 */
public class D2_MultiThreadNioEchoServer {

    private static Map<Socket, Long> geym_time_stat = new HashMap<>(10240);
    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        D2_MultiThreadNioEchoServer echoServer = new D2_MultiThreadNioEchoServer();
        try {
            echoServer.startServer();
        } catch (Exception e) {
            System.out.println("Exception caught,program exiting...");
            e.printStackTrace();
        }
    }

    /**
     * Accept a new client and set it up for reading
     *
     * @param sk SelectionKey
     */
    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false); // 设置为非阻塞模式

            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);

            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress inetAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + inetAddress.getHostAddress());

        } catch (Exception e) {
            System.out.println("Failed to accept new client.");
            e.printStackTrace();
        }
    }

    private void startServer() throws IOException {
        Selector open = Selector.open();
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 非阻塞模式下， server不会做等待？
        ssc.configureBlocking(false);   // 这是是否为阻塞模式？此处设置为非阻塞

        // socket绑定到8000端口
        InetSocketAddress isa = new InetSocketAddress(8008);
        ssc.socket().bind(isa);

        SelectionKey register = ssc.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            int select = selector.select();

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            long e = 0;
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();  // 为什么要remove呢？会重复处理？

                Socket socket;
                if (sk.isAcceptable()) {
                    //
                    doAccept(sk);
                } else if (sk.isValid() && sk.isReadable()) {
                    socket = ((SocketChannel) sk.channel()).socket();
                    // 记录时间
                    if (!geym_time_stat.containsKey(socket)) {
                        geym_time_stat.put(socket, System.currentTimeMillis());
                    }
                    doRead(sk);
                } else if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);
                    socket = ((SocketChannel) sk.channel()).socket();
                    e = System.currentTimeMillis();
                    long b = geym_time_stat.remove(socket);
                    System.out.println("spend:" + (e - b) + "ms");
                }
            }
        }
    }

    /**
     * 写数据
     *
     * @param sk SelectionKey
     * @author zhaojy
     */
    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outputQueue = echoClient.getOutputQueue();

        ByteBuffer bb = outputQueue.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }

            // ?
            if (bb.remaining() == 0) {
                outputQueue.removeLast();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to write to client..");
            disconnect(sk);
        }

        // 数据会写完毕，更改模式为只读
        if (outputQueue.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void disconnect(SelectionKey sk) {
    }

    /**
     * @param sk SelectionKey
     */
    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;
        try {
            // 数据读到buffer中
            len = channel.read(bb);
            if (len < 0) {
                disconnect(sk);
            }

        } catch (Exception e) {
            System.out.println("Failed to read from client..");
            e.printStackTrace();
            disconnect(sk);
        }

        // 转换模式
        bb.flip();

        // 开线程，读数据
        tp.execute(new HandleMsg(sk, bb));
    }

    class EchoClient {
        private LinkedList<ByteBuffer> byteBuffers;

        public EchoClient() {
            byteBuffers = new LinkedList<>();
        }

        public LinkedList<ByteBuffer> getOutputQueue() {
            return byteBuffers;
        }

        public void enqueue(ByteBuffer buffer) {
            byteBuffers.addFirst(buffer);
        }
    }

    class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();
            echoClient.enqueue(bb);

            // 修改模式，改为可写模式
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();
        }
    }

}
