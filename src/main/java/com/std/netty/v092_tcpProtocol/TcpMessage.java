package com.std.netty.v092_tcpProtocol;

import lombok.Data;

/**
 * @author zhaojy
 * @date 2020/3/22 12:52
 */
@Data
public class TcpMessage {

    /**
     * 消息长度
     */
    private int length;


    /**
     * 消息体
     */
    private byte[] content;
}
