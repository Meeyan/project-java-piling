package com.std.netty.v016_channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author zhaojy
 * @date 2020/3/1 2:04 PM
 */
public class NioFileChannel04 {
    public static void main(String[] args) throws Exception {
        //
        FileInputStream inputStream = new FileInputStream(new File("/Users/zhaojy/Desktop/file.txt"));
        FileOutputStream outputStream = new FileOutputStream(new File("/Users/zhaojy/Desktop/file-2.txt"));

        // 获取channel
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        // 直接使用channel完成文件拷贝
        outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());

        inputStreamChannel.close();
        outputStreamChannel.close();
        outputStream.close();
        inputStream.close();

    }
}
