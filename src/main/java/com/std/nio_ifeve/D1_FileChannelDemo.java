package com.std.nio_ifeve;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel示例
 *
 * @author zhaojy
 * @date 2018-01-05
 */
public class D1_FileChannelDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("D:\\demodir\\local\\fileChannel.txt", "rw");
        FileChannel fileChannel = file.getChannel();    // 打开channel
        ByteBuffer byteBuffer = ByteBuffer.allocate(200);  // 创建缓冲区
        int read = fileChannel.read(byteBuffer);
        StringBuilder sb = new StringBuilder();
        while (read != -1) {
            System.out.println("Read " + read);

            byteBuffer.flip();    // 转换模式

            byte[] array = byteBuffer.array();
            sb.append(new String(array));

            /*while (byteBuffer.hasRemaining()) {
                byteBuffer.get();
            }*/

            byteBuffer.clear();   // 清掉，重新读
            read = fileChannel.read(byteBuffer);
        }
        file.close();
        System.out.println(sb.toString());
    }
}
