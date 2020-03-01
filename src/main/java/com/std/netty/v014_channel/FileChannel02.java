package com.std.netty.v014_channel;


import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 从文件读取数据
 * 1. 获取InputStream 或者 OutputStream
 * 2. 从流里获取channel
 * 3. 创建缓冲区（byteBuffer)
 * 4. 将数据写入缓冲区  / 或者将流中数据读入缓冲区
 * 5. 切换模式（flip）
 * 6. 缓冲区数据写入channel 或者 显示数据
 * 7. close
 *
 * @author zhaojy
 * @date 2020/2/29 11:46 PM
 */
public class FileChannel02 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/zhaojy/Desktop/file.txt"));

        // 获取channel
        FileChannel channel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(fileInputStream.available());

        // 将流里的数据读取到byteBuffer中
        channel.read(byteBuffer);

        // show content
        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();


    }
}
