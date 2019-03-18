package com.std.gof23.L015_command;

/**
 * 命令实现类，服务于某一个命令模式
 *
 * @author zhaojy
 * @date 2018-03-14
 */
public class ConcreteCommand implements Command {

    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.action();
    }
}
