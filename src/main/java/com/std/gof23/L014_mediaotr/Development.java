package com.std.gof23.L014_mediaotr;

/**
 * 研发部
 *
 * @author zhaojy
 * @date 2018-03-13
 */
public class Development implements Department {

    private Mediator mediator;

    public Development(Mediator mediator) {
        this.mediator = mediator;
        mediator.register("development",this);
    }

    @Override
    public void selfAction() {
        System.out.println("专心科研，不要搞事情");
    }

    @Override
    public void outAction() {
        System.out.println("汇报工作，没钱了，需要资金支持");
    }
}
