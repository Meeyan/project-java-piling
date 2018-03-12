package com.std.gof23.L04_Builder;

/**
 * @author zhaojy
 * @create-time 2018-03-07
 */
public class Client {
    public static void main(String[] args) {
        AirShipDirector director = new ZjyAirShipDirector(new ZjyAirShipBuilder());
        AirShip airShip = director.directorAirShip();
        System.out.println(airShip.getEngine());
    }
}
