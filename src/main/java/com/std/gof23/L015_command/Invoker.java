package com.std.gof23.L015_command;

/**
 * 命令的调用者和发起者
 *
 * @author zhaojy
 * @create-time 2018-03-14
 */
public class Invoker {

    // 也可以通过容器容纳很多命令。数据库底层的事务处理就是这个模式
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    /**
     * 调用命令的执行方法
     */
    public void call() {
        command.execute();

    }
}
