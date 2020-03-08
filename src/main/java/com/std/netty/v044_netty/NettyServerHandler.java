package com.std.netty.v044_netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * WorkerGroup 任务处理handler
 *
 * @author zhaojy
 * @date 2020/3/8 13:11
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据（我们可以从客户端接收消息）
     *
     * @param ctx ChannelHandlerContext 上下文对象，含有管道PipeLine，地址等
     * @param msg Object 客户端消息体
     * @throws Exception Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("server ctx:" + ctx);

        /**
         * 当服务端此处的业务逻辑较重，耗时较长时，可以使用如下方法完成。
         * 1. 异步队列完成处理，注意队列的特性，会有排队。
         * 2. 用户自定义定时任务
         * 3. 不同channel同步数据
         */

        // 异步队列完成
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10000L);
                // 将数据写入缓存，并刷新
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端,服务端处理完了，你可可以继续了，喵喵喵-1", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(10000L);
                // 将数据写入缓存，并刷新
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端,服务端处理完了，你可可以继续了，喵喵喵-2", CharsetUtil.UTF_8));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 5, TimeUnit.SECONDS);


        // 此ByteBuf和Nio ByteBuffer不是一个，由netty封装实现，性能更好
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println("客户端发送消息是：" + byteBuf.toString(CharsetUtil.UTF_8));

        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完成后的触发事件
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // 将数据写入缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
