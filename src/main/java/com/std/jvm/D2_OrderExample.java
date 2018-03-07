package com.std.jvm;

/**
 * @author zhaojy
 * @create-time 2018-02-24
 */
public class D2_OrderExample {
    int a = 0;
    boolean flag = false;

    public synchronized void writer() throws InterruptedException {
        a = 1;
        flag = true;
        Thread.sleep(5000);
    }

    public synchronized void reader() {
        if (flag) {
            int i = a + 1;
            System.out.println(i);
        }
    }

    static class ReaderThread implements Runnable {
        D2_OrderExample example;

        public ReaderThread(D2_OrderExample example) {
            this.example = example;
        }

        @Override
        public void run() {
            this.example.reader();
        }
    }

    static class WriterThread implements Runnable {
        D2_OrderExample example;

        public WriterThread(D2_OrderExample example) {
            this.example = example;
        }

        @Override
        public void run() {
            try {
                this.example.writer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Runtime.getRuntime().maxMemory() / 1024.0 / 1024);
        System.out.println(Runtime.getRuntime().freeMemory() / 1024.0 / 1024);
        System.out.println(Runtime.getRuntime().totalMemory() / 1024.0 / 1024);
        D2_OrderExample example = new D2_OrderExample();

        Thread writer = new Thread(new WriterThread(example));
        Thread reader = new Thread(new ReaderThread(example));

        writer.start();
        reader.start();

        writer.join();

    }
}
