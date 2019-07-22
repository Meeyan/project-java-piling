package com;

public class FinalReferenceEscapeExample {

    final int i;
    static FinalReferenceEscapeExample obj;

    public FinalReferenceEscapeExample() {
        this.i = 1;
        obj = this;
    }

    public static void writer() {
        new FinalReferenceEscapeExample();
    }

    public static void reader() {
        if (obj != null) {
            int tmp = obj.i;
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FinalReferenceEscapeExample.writer();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                FinalReferenceEscapeExample.reader();
            }
        }).start();
    }

}
