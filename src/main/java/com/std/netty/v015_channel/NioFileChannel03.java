package com.std.netty.v015_channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 用channel完成文件拷贝
 *
 * @author zhaojy
 * @date 2020/3/1 1:41 PM
 */
public class NioFileChannel03 {
    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("/Users/zhaojy/Desktop/file.txt");

        FileChannel inChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/zhaojy/Desktop/file-des.txt");

        FileChannel outChannel = fileOutputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            // 读之前清空原有数据 或者 在buffer写之后再次flip也行
            byteBuffer.clear();
            int read = inChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            // 将缓冲区中的数据写到outChannel中
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            //  byteBuffer.flip();
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
