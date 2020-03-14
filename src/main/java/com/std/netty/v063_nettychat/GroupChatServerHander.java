package com.std.netty.v063_nettychat;

import cn.hutool.core.date.DateUtil;
import io.netty.channel.Channel;
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
        Channel channel = ctx.channel();
        // 发送消息时，需要排除自己
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送消息:" + msg + "\n");
            } else {
                ch.writeAndFlush("[自己]发送了消息" + msg + "\n");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 用来标识用户上线
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        channelGroup.writeAndFlush(ctx.channel().remoteAddress() + " 上线了~" + DateUtil.now());
    }

    /**
     * 表示用户离开
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("group size:" + channelGroup.size());
        channelGroup.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + " 离开了");
        // 会自动从channelGroup中remove掉当前ctx
        System.out.println("group size:" + channelGroup.size());
    }

    /**
     * @param ctx ChannelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了");
    }

    /**
     * 表示连接建立，一旦连接，该方法第一个被执行
     * 将当前channel加入到 channelGroup
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        // 该方法会将channelGroup中的所有的channel遍历，发送消息，不需要我们手动遍历
        channelGroup.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + " 加入聊天\n");

        channelGroup.add(ctx.channel());
    }
}
