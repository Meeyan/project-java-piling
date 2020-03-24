package com.std.netty.v092_tcpProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义tcp数据体协议规范
 * 用来将TcpMessage对象转成byte数据
 *
 * @author zhaojy
 * @date 2020/3/22 12:55
 */
public class TcpProMessageEncoder extends MessageToByteEncoder<TcpMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, TcpMessage msg, ByteBuf out) throws Exception {
        System.out.println("TcpProMessageEncoder encode被调用");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
