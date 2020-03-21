package com.std.netty.v061_byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * ByteBuf的原理
 *
 * @author zhaojy
 * @date 2020/3/10 1:10 PM
 */
public class ByteBufTest {


    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        /**
         * 创建一个10个大小的buf)
         * 1. 在netty中，ByteBuf不需要使用flip进行反转（nio中的ByteBuffer需要）
         * 2. 底层维护了 readerIndex 和 writeIndex
         * 3. 跟踪代码可以发现，readerIndex, writerIndex, capacity 将buffer分成了三个区：
         *  -> 0 ~ readerIndex： 已经读取的区域
         *  -> readerIndex ~ writerIndex：可读的区域
         *  -> writerIndex ~ capacity：可写的区域
         */
        ByteBuf buffer = Unpooled.buffer(10);

        // writerIndex会随着变化
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity:" + buffer.capacity());


        // 此操作不会触发readerIndex的变化
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }

        // readByte的调用方法会改变readerIndex
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }

        System.out.println("执行完毕");
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }


}
