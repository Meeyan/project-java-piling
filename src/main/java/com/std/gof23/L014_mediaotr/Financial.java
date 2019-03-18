package com.std.gof23.L014_mediaotr;

/**
 * 研发部
 *
 * @author zhaojy
 * @date 2018-03-13
 */
public class Financial implements Department {

    private Mediator mediator;

    public Financial(Mediator mediator) {
        this.mediator = mediator;
        mediator.register("Financial", this);
    }

    @Override
    public void selfAction() {
        System.out.println("专心数钱，要细心");
    }

    @Override
    public void outAction() {
        System.out.println("汇报工作，没钱了，钱太堵了，怎么画。。");
    }
}
