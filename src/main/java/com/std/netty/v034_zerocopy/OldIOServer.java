package com.std.netty.v034_zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统nio服务端
 *
 * @author zhaojy
 * @date 2020/3/7
 */
public class OldIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(7001);

        while (true) {
            Socket socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[4096];
            while (true) {
                try {
                    int readCount = dataInputStream.read(bytes, 0, bytes.length);
                    if (-1 == readCount) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }

            }
        }
    }
}
