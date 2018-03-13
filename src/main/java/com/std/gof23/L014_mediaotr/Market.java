package com.std.gof23.L014_mediaotr;

/**
 * 研发部
 *
 * @author zhaojy
 * @create-time 2018-03-13
 */
public class Market implements Department {

    private Mediator mediator;

    public Market(Mediator mediator) {
        this.mediator = mediator;
        mediator.register("Market", this);
    }

    @Override
    public void selfAction() {
        System.out.println("专心数钱，搞关系");
    }

    @Override
    public void outAction() {
        System.out.println("汇报工作，需要资金支持跑项目");
    }
}
