package com.std.netty.v018_channel;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以让文件直接在内存（堆外内存）中进行修改
 *
 * @author zhaojy
 * @date 2020/3/1 6:40 PM
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/zhaojy/Desktop/file.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();


        /**
         * 1. 读写模式
         * 2. 可以修改的起始位置
         * 3. 映射到内存的大小，(不是索引位置），即将 1.txt的多少个字节映射到内存，可以直接修改的范围是0~5，超过5报错，mappedByteBuffer只会读取指定的数据到内存中
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) 'L');
        mappedByteBuffer.put(5, (byte) 'M');

        randomAccessFile.close();


    }
}
