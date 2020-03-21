package com.std.netty.v080_inOutBound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author zhaojy
 * @date 2020/3/20 22:52
 */
public class BoundClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器的ip=" + ctx.channel().remoteAddress());
        System.out.println("收到服务器的消息：" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BoundClientHandler 发送数据了");

        // 发送一个long
        // ctx.writeAndFlush(123268L);

        // 1. 发送这样的一个串时，服务端会发生什么？服务端一次解析一个long数据，MyByteToLongDecoder被调用2次
        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));

        /**
         * 2. 该类处理的前一个handler 是 MyLongToByteEncoder
         * 3. MyLongToByteEncoder 父类是 MessageToByteEncoder
         * 4. 父类：MessageToByteEncoder
         */
        /*
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            ByteBuf buf = null;
            try {
                // 判断当前的msg，是不是应该处理的类型，如果是就处理，否则就执行else
                if (acceptOutboundMessage(msg)) {
                    @SuppressWarnings("unchecked")
                    I cast = (I) msg;
                    buf = allocateBuffer(ctx, cast, preferDirect);
                    try {
                        encode(ctx, cast, buf);
                    } finally {
                        ReferenceCountUtil.release(cast);
                    }

                    if (buf.isReadable()) {
                        ctx.write(buf, promise);
                    } else {
                        buf.release();
                        ctx.write(Unpooled.EMPTY_BUFFER, promise);
                    }
                    buf = null;
                } else {
                    ctx.write(msg, promise);
                }
            } catch (EncoderException e) {
                throw e;
            } catch (Throwable e) {
                throw new EncoderException(e);
            } finally {
                if (buf != null) {
                    buf.release();
                }
            }
        }
         */


    }
}
