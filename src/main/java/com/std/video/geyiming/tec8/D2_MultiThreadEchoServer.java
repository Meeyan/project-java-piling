package com.std.video.geyiming.tec8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟多线程问题：未使用NIO之前
 *
 * @author zhaojy
 * @create-time 2017-12-29
 */
public class D2_MultiThreadEchoServer {

    private static ExecutorService tp = Executors.newCachedThreadPool();

    static class HandleMsg implements Runnable {
        Socket socket;

        public HandleMsg(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader bfr = null;
            PrintWriter pw = null;
            try {
                // 获取链接到的客户端的数据
                bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw = new PrintWriter(socket.getOutputStream(), true);
                // 从InputStream中读取客户端发送的数据
                String inputLine = null;
                long start = System.currentTimeMillis();
                while ((inputLine = bfr.readLine()) != null) {
                    pw.println(inputLine);
                }
                long end = System.currentTimeMillis();
                System.out.println("spend:" + (end - start) + " ms");
            } catch (Exception e) {
                // deal exception here
                e.printStackTrace();
            } finally {
                // do some close here
                try {
                    if (bfr != null) bfr.close();
                    if (pw != null) pw.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket echoServer = null;
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(8008);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                clientSocket = echoServer.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + " connect ");
                tp.execute(new HandleMsg(clientSocket));    // 由线程池运行
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
