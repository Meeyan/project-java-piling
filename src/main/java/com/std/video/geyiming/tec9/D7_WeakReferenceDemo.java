package com.std.video.geyiming.tec9;

import java.lang.ref.WeakReference;

/**
 * WeakReference示例
 *
 * @author zhaojy
 * @date 2018-01-15
 */
public class D7_WeakReferenceDemo {

    public static void main(String[] args) {
        Car car = new Car(20000, "red");
        WeakReference<Car> weakCar = new WeakReference<Car>(car);

        int i = 0;
        while (true) {
            if (weakCar.get() != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops" + weakCar);
            } else {
                System.out.println("Object has been gc..");
                break;
            }
        }

    }
}

class Car {
    private double price;
    private String color;

    public Car(double price, String color) {
        this.price = price;
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color + ",car cost:" + price;
    }
}

