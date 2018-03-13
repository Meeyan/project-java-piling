package com.std.gof23.L12_chain;

/**
 * 总经理
 *
 * @author zhaojy
 * @create-time 2018-03-13
 */
public class GeneralManager extends Leader {
    public GeneralManager(String name) {
        super(name);
    }

    @Override
    void handleRequest(LeaveRequest request) {
        if (request.getLeaveDays() < 30) {
            // 审批
            System.out.println("员工：" + request.getEmpName() + ",请假：" + request.getLeaveDays() + "天，原因：" + request.getReason());
            System.out.println("总经理：" + name + "，审批通过");
        } else {
            System.out.println("滚蛋吧");
        }
    }
}
