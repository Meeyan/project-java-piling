package com.std.netty.v080_inOutBound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhaojy
 * @date 2020/3/20 22:50
 */
public class BoundClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个出站的handler，对数据进行编码 -- outBound
        pipeline.addLast(new MyLongToByteEncoder());

        // 入站 inBound
        pipeline.addLast(new MyByteToLongDecoderNew());

        // 加入一个自定义handler，处理逻辑
        pipeline.addLast(new BoundClientHandler());

    }
}
