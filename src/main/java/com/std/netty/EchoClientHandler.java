package com.std.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaojy
 * @date 2020/2/21 2:34 PM
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String clientPrefix = "client-" + Thread.currentThread().getId() + "-";

    /**
     * 在到服务器的连接已经建立之后将被调用
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (ctx.isRemoved()) {
            return;
        }
        final ByteBuf buffer = ctx.alloc().buffer(8);
        buffer.writeBytes("连接已经建立，我要发送消息了。".getBytes());
        ctx.writeAndFlush(buffer);
        logger.info("EchoClientHandler 有新连接");


        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(30000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final ByteBuf buffer = ctx.alloc().buffer(8);
                    String[] dataArray = {"unionman", "other"};
                    String deviceType = dataArray[0];
                    if (System.currentTimeMillis() % 2 == 0) {
                        deviceType = dataArray[1];
                    }
                    buffer.writeBytes((deviceType).getBytes());
                    ctx.writeAndFlush(buffer);
                }
            }
        }.start();
    }

    /**
     * 当从服务器接收到一个消息时被调用
     *
     * @param channelHandlerContext ChannelHandlerContext
     * @param byteBuf               ByteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        try {
            byte[] resultBytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(resultBytes);
            String result = new String(resultBytes);
            logger.error("收到服务端消息：" + result);
        } finally {
            // byteBuf.release();
        }


    }

    /**
     * 在处理过程中引发异常时被调用
     *
     * @param ctx   ChannelHandlerContext
     * @param cause Throwable
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
