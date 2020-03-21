package com.std.netty.v080_inOutBound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * decode 由byte to long
 *
 * @author zhaojy
 * @date 2020/3/20 22:45
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * @param ctx ChannelHandlerContext
     * @param in  ByteBuf
     * @param out List<Object> 将解码后的数据传递给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder decode 被调用");
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
