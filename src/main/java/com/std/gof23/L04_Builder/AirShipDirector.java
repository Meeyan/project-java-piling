package com.std.gof23.L04_Builder;

/**
 * 飞船装配器
 *
 * @author zhaojy
 * @date 2018-03-07
 */
public interface AirShipDirector {

    /**
     * 装配飞船
     *
     * @return AirShip
     */
    AirShip directorAirShip();
}
