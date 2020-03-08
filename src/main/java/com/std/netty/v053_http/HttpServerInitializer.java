package com.std.netty.v053_http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author zhaojy
 * @date 2020/3/8 17:32
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器

        // 得到管道

        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个netty 提供的 HttpServerCodec codec 【 coder -> decoder】
        // 1. 加入一个netty提供的 编-解码器
        // pipeline.addLast("myHttpCoder", new HttpServerCodec());

        // 2. 加入一个自定义的处理器
        pipeline.addLast("myHttpHandler", new HttpServerHandler());
    }
}
