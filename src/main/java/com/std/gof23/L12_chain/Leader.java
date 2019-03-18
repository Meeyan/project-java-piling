package com.std.gof23.L12_chain;

/**
 * 领导的抽象类
 *
 * @author zhaojy
 * @date 2018-03-13
 */
public abstract class Leader {
    protected String name;
    protected Leader nextLeader;

    public Leader(String name) {
        this.name = name;
    }

    public void setNextLeader(Leader nextLeader) {
        this.nextLeader = nextLeader;
    }

    /**
     * 处理请求的核心的业务方法
     *
     * @param request
     */
    abstract void handleRequest(LeaveRequest request);
}
