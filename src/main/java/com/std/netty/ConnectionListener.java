package com.std.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaojy
 * @date 2020/2/27 4:07 PM
 */
public class ConnectionListener implements ChannelFutureListener {

    private EchoClient echoClient;

    public ConnectionListener(EchoClient echoClient) {
        this.echoClient = echoClient;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            System.out.println("Reconnect");
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        echoClient.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1L, TimeUnit.SECONDS);
        }

    }
}
