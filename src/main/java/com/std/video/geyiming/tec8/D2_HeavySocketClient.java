package com.std.video.geyiming.tec8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * 模拟客户端，有异常的
 *
 * @author zhaojy
 * @date 2017-12-29
 */
public class D2_HeavySocketClient {

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static final int sleepTime = 1000 * 1000 * 1000;

    public static class EchoClient implements Runnable {

        @Override
        public void run() {
            Socket client = null;
            PrintWriter writer = null;
            BufferedReader bfReader = null;

            try {
                client = new Socket();
                client.connect(new InetSocketAddress("localhost", 8008));
                writer = new PrintWriter(client.getOutputStream(), true);
                // 没输出一个字符休息一会儿，模拟网络问题的现象
                writer.print("H");
                LockSupport.parkNanos(sleepTime);
                writer.print("e");
                LockSupport.parkNanos(sleepTime);
                writer.print("l");
                LockSupport.parkNanos(sleepTime);
                writer.print("l");
                LockSupport.parkNanos(sleepTime);
                writer.print("o");
                LockSupport.parkNanos(sleepTime);
                writer.print("!");
                writer.println();
                writer.flush();

                bfReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server:" + bfReader.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // do some close here
                try {
                    if (null != writer)
                        writer.close();
                    if (null != bfReader)
                        bfReader.close();
                    if (null != client)
                        client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        EchoClient client = new EchoClient();
        for (int i = 0; i < 10; i++) {
            executorService.execute(client);
        }
    }
}
