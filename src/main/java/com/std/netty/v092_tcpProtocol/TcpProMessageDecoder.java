package com.std.netty.v092_tcpProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 自定义tcp数据体协议规范
 * 用来将byte数据转成TcpMessage对象传入下层
 *
 * @author zhaojy
 * @date 2020/3/22 12:55
 */
public class TcpProMessageDecoder extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("TcpProMessageDecoder decode 被调用");
        int contentLength = in.readInt();
        byte[] content = new byte[contentLength];
        in.readBytes(content);

        // 将信息封装成TcpMessage传入下层
        TcpMessage tcpMessage = new TcpMessage();
        tcpMessage.setLength(contentLength);
        tcpMessage.setContent(content);
        out.add(tcpMessage);
    }
}
