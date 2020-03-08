package com.std.netty.v053_http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * HttpObject：客户端和服务器端相互通信的数据被封装成 HttpObject
 *
 * @author zhaojy
 * @date 2020/3/8 17:36
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    /**
     * 读取客户端数据
     *
     * @param ctx ChannelHandlerContext
     * @param msg HttpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型" + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            ByteBuf byteBuf = Unpooled.copiedBuffer("hello，我是服务器", CharsetUtil.UTF_8);

            DefaultFullHttpResponse response =
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            ctx.writeAndFlush(response);
        }
    }

}