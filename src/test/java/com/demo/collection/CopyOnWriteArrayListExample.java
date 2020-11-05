package com.demo.collection;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 用来代替 Vector
 * 多线程安全的 List 方案
 *
 * @author zhaojy
 * @date 2020/11/5 10:31 上午
 */
public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> cowList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            cowList.add(i);
        }

        PutThread putThread = new PutThread("putThread-1", cowList);
        putThread.start();

        Iterator<Integer> iterator = cowList.iterator();

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

        iterator = cowList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        System.out.println();
    }
}

class PutThread extends Thread {

    private CopyOnWriteArrayList<Integer> cowal;

    public PutThread(String name, CopyOnWriteArrayList<Integer> cowal) {
        super(name);
        this.cowal = cowal;
    }

    @Override
    public void run() {
        try {
            for (int i = 100; i < 110; i++) {
                cowal.add(i);
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
