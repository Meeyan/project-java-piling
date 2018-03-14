package com.std.gof23.L015_command;

/**
 *
 * @author zhaojy
 * @create-time 2018-03-14
 */
public class Client {

    public static void main(String[] args) {
        Command c = new ConcreteCommand(new Receiver());    // 指定命令，指定执行者
        Invoker invoker = new Invoker(c);   // 调用者:分发命令，通知执行，解耦
        invoker.call();
    }

}
