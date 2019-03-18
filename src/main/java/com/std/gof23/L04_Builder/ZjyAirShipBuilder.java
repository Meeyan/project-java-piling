package com.std.gof23.L04_Builder;

/**
 * 无敌牌飞船构造器
 *
 * @author zhaojy
 * @date 2018-03-07
 */
public class ZjyAirShipBuilder implements AirShipBuilder {
    @Override
    public Engine buildEngine() {
        System.out.println("创建发动机");
        return new Engine("无敌牌发动机");
    }

    @Override
    public OrbitalModule buildOrbitalModule() {
        System.out.println("创建轨道舱");
        return new OrbitalModule("无敌牌轨道舱");
    }

    @Override
    public EscapeTower buildEscapeTower() {
        System.out.println("创建逃逸塔");
        return new EscapeTower("无敌牌逃逸塔");
    }
}
