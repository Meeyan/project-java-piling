package com.std.video.geyiming.tec8;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * nio-demo
 *
 * @author zhaojy
 * @create-time 2017-12-28
 */
public class D1_NioDemo {

    /**
     * nio拷贝文件
     *
     * @param resource String
     * @param dest     String
     * @throws IOException
     * @author zhaojy
     * @createTime 2017-12-28
     */
    public static void nioCopyFile(String resource, String dest) throws IOException {
        FileInputStream fis = new FileInputStream(resource);
        FileOutputStream fos = new FileOutputStream(dest);

        // 1. 获取一个channel
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();

        // 2. 搞一个缓冲区，1K大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.rewind();
        while (true) {
            // 3. 清空缓冲区
            buffer.clear();

            // 4. 将数据读入缓冲区，切记：读取前一定要清空buffer
            int len = fisChannel.read(buffer);
            if (len == -1) {
                break;  // 读取完毕
            }

            // 5. 读写模式转换
            buffer.flip();

            fosChannel.write(buffer);   // 写数据:从buffer写入到channel中
        }

        // 6. 关闭通道chanel
        fisChannel.close();
        fosChannel.close();
    }


    public static void main(String[] args) throws IOException {
        String srcFile = "D:\\demodir\\fol1\\1111.txt";
        String destFile = "D:\\demodir\\fol1\\2222.txt";
        nioCopyFile(srcFile, destFile);
    }
}
