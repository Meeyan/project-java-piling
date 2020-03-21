package com.std.netty.v080_inOutBound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhaojy
 * @date 2020/3/20 22:44
 */
public class BoundServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 入站的handler进行解码--inBound
        // pipeline.addLast(new MyByteToLongDecoder());

        // 优化后的decoder
        pipeline.addLast(new MyByteToLongDecoderNew());

        // 出站encoder使用--outBound,decoder和encoder并不冲突
        pipeline.addLast(new MyLongToByteEncoder());

        // 自定义的handler，处理业务逻辑
        pipeline.addLast(new BoundServerHandler());
    }
}
