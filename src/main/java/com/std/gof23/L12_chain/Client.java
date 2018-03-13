package com.std.gof23.L12_chain;

/**
 * @author zhaojy
 * @create-time 2018-03-13
 */
public class Client {
    public static void main(String[] args) {
        Leader a = new Director("张三");
        Leader b = new Manager("李四");
        Leader c = new GeneralManager("王武");

        // 组织责任链对象关系
        a.setNextLeader(b);
        b.setNextLeader(c);

        LeaveRequest request = new LeaveRequest("Jim", 12, "回美国老家");
        a.handleRequest(request);

    }
}
