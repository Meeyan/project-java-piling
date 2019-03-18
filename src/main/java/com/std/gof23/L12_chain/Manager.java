package com.std.gof23.L12_chain;

/**
 * 经历
 *
 * @author zhaojy
 * @date 2018-03-13
 */
public class Manager extends Leader {
    public Manager(String name) {
        super(name);
    }

    @Override
    void handleRequest(LeaveRequest request) {
        if (request.getLeaveDays() < 10) {
            // 审批
            System.out.println("员工：" + request.getEmpName() + ",请假：" + request.getLeaveDays() + "天，原因：" + request.getReason());
            System.out.println("经理：" + name + "，审批通过");
        } else {
            if (this.nextLeader != null)
                nextLeader.handleRequest(request);
        }
    }
}
