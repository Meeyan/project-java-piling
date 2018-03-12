package com.std.gof23.L04_Builder;

/**
 * 飞船构造器
 *
 * @author zhaojy
 * @create-time 2018-03-07
 */
public interface AirShipBuilder {
    Engine buildEngine();

    OrbitalModule buildOrbitalModule();

    EscapeTower buildEscapeTower();
}
