package com.std.BFBCDYS.v0040;

import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * @author zhaojy
 * @date 2019/10/26 1:44 PM
 */
public class ExchangerDemo {
    private Exchanger<DataBuffer> exchanger = new Exchanger<>();

    private DataBuffer initialEmptyBuffer = new DataBuffer();

    private DataBuffer initialFullBuffer = new DataBuffer();

    class FillingLoop implements Runnable {

        @Override
        public void run() {
            DataBuffer currentBuffer = initialEmptyBuffer;
            try {
                while (currentBuffer.length() == 0) {
                    addToBuffer(currentBuffer);
                    if (currentBuffer.isFull()) {
                        currentBuffer = exchanger.exchange(currentBuffer);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class EmptyingLoop implements Runnable {


        @Override
        public void run() {
            // 模拟箱子满了
            addToBuffer(initialFullBuffer);

            DataBuffer currentBuffer = initialFullBuffer;

            while (currentBuffer.isFull()) {
                takeFromBuffer(currentBuffer);
                if (!currentBuffer.isFull()) {
                    try {
                        currentBuffer = exchanger.exchange(currentBuffer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void addToBuffer(DataBuffer emptyBuffer) {
        Integer[] arr = emptyBuffer.getArr();
        Random random = new Random(200);
        for (int i = 0; i < DataBuffer.MAX_SIZE; i++) {
            arr[i] = random.nextInt();
        }
        emptyBuffer.setFull(true);
        System.out.println("苹果箱满了....");
    }

    public void takeFromBuffer(DataBuffer fullBuffer) {
        Integer[] arr = fullBuffer.getArr();
        for (int i = 0; i < DataBuffer.MAX_SIZE; i++) {
            arr[i] = null;
        }
        fullBuffer.setFull(false);
        System.out.println("苹果空了....");
    }

    void start() {
        new Thread(new EmptyingLoop()).start();
        new Thread(new FillingLoop()).start();
    }

    public static void main(String[] args) {
        ExchangerDemo exchangerDemo = new ExchangerDemo();
        exchangerDemo.start();
    }
}

class DataBuffer {
    static final int MAX_SIZE = 100;
    private Integer[] arr = new Integer[MAX_SIZE];
    private boolean full = false;
    private int length = 0;

    public int length() {
        return this.length;
    }

    public void setFull(boolean full) {
        this.full = full;
        if (full) {
            this.length = MAX_SIZE;
        } else {
            this.length = 0;
        }
    }

    public boolean isFull() {
        return full;
    }

    public Integer[] getArr() {
        return arr;
    }

    public void setArr(Integer[] arr) {
        this.arr = arr;
    }
}
