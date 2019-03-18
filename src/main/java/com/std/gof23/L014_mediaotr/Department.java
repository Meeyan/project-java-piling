package com.std.gof23.L014_mediaotr;

/**
 * @author zhaojy
 * @date 2018-03-13
 */
public interface Department {

    /**
     * 做本部门的事情
     */
    void selfAction();

    /**
     * 向总经理发出申请
     */
    void outAction();
}
