package com.std.netty.v063_nettychat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * server端工作对象
 *
 * @author zhaojy
 * @date 2020/3/11 22:21
 */
public class GroupChatServerHander extends SimpleChannelInboundHandler<String> {


    /**
     * 这是干啥的？
     * 定义一个channel组，管理所有的channel
     * GlobalEventExecutor.INSTANCE 是全局的事件执行器，单例对象
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }

    /**
     * 表示连接建立，一旦连接，该方法第一个被执行
     * 将当前channel加入到 channelGroup
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
        super.handlerAdded(ctx);
    }
}
