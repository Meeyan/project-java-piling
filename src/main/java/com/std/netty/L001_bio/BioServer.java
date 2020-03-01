package com.std.netty.L001_bio;

import cn.hutool.core.io.IoUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaojy
 * @date 2020/2/24 10:15 PM
 */
public class BioServer {
    public static void main(String[] args) throws IOException {

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");

        while (true) {

            // todo 此处会阻塞，每次一个新的连接建立后，此处仍旧阻塞，等地下一个连接进来
            final Socket socket = serverSocket.accept();

            System.out.println("一个客户端连接过来了");

            cachedThreadPool.execute(() -> {
                // 可以和客户端通信
                handler(socket);
            });
        }

    }

    /**
     * 处理和客户端的通信
     *
     * @param socket Socket
     */
    public static void handler(Socket socket) {

        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();

            // 循环读取客户端发送的数据
            while (true) {

                // todo 此处会阻塞，等待客户端发送数据，数据不发送完毕，会一直阻塞住
                int readLength = inputStream.read(bytes);
                // -1 表示结束
                if (readLength != -1) {
                    System.out.println(new String(bytes, 0, readLength));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            IoUtil.close(socket);
        }
    }

}
