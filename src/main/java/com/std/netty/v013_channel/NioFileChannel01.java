package com.std.netty.v013_channel;

import java.io.FileOutputStream;
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
 * @author zhaojy
 * @date 2020/2/28 12:36 AM
 */
public class NioFileChannel01 {
    public static void main(String[] args) throws Exception {

        String str = "hello 尚硅谷";

        // FileOutputStream 包裹了Channel
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/zhaojy/Desktop/file.txt");

        // 此处的真实类型是：FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区：写
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

        // 将str写入到buffer中
        writeBuffer.put(str.getBytes());

        // flip,切换写 -> 读模式
        writeBuffer.flip();


        // 将buffer 数据写入到fileChannel
        fileChannel.write(writeBuffer);

        fileOutputStream.close();

    }
}
