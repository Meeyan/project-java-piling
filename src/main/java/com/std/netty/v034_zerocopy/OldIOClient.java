package com.std.netty.v034_zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * 传统io 客户端
 * 测试发现，无论服务端是否适用nio模式，客户端适用 传统IO 和 NIO零拷贝模式，性能差距很大。
 *
 * @author zhaojy
 * @date 2020/3/7
 */
public class OldIOClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 7001);

        String fileName = "C:\\Users\\zhaojy\\Desktop\\Microsoft+Visual+Studio+Pro+2019.zip";

        FileInputStream fileInputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[4096];
        long readCount;

        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = fileInputStream.read(bytes)) >= 0) {
            total += readCount;

            dataOutputStream.write(bytes);
        }
        System.out.println("发送的总字节数：" + total + "，总耗时:" + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        fileInputStream.close();
        socket.close();
    }
}
