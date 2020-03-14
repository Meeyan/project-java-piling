package com.std.netty.v068_websocket;

import cn.hutool.core.date.DateUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * TextWebSocketFrame 表示一个文本帧
 *
 * @author zhaojy
 * @date 2020/3/14 16:44
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器端收到信息：" + msg.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间：" + DateUtil.now() + " " + msg.text()));
    }

    /**
     * 客户端连接后，会触发该方法
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // id 表示唯一的值，LongText是唯一的，shortText不是唯一 ？？
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());

    }

    /**
     * 断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
    }

    /**
     * 异常发生
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生" + ctx.channel().remoteAddress() + ", " + cause.getMessage());
        ctx.close();
    }
}
