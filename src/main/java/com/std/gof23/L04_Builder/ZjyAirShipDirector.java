package com.std.gof23.L04_Builder;

/**
 * 飞船装配器
 *
 * @author zhaojy
 * @create-time 2018-03-07
 */
public class ZjyAirShipDirector implements AirShipDirector {

    private ZjyAirShipBuilder builder;

    public ZjyAirShipDirector(ZjyAirShipBuilder builder) {
        this.builder = builder;
    }

    public ZjyAirShipBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(ZjyAirShipBuilder builder) {
        this.builder = builder;
    }

    @Override
    public AirShip directorAirShip() {
        Engine engine = builder.buildEngine();
        EscapeTower escapeTower = builder.buildEscapeTower();
        OrbitalModule orbitalModule = builder.buildOrbitalModule();
        AirShip ship = new AirShip();
        ship.setEngine(engine);
        ship.setEscapeTower(escapeTower);
        ship.setOrbitalModule(orbitalModule);
        return ship;
    }
}
