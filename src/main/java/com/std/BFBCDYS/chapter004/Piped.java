package com.std.BFBCDYS.chapter004;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 线程间通信-piped
 */
public class Piped {
    public static void main(String[] args) {
        try {
            PipedWriter out = new PipedWriter();


            PipedReader reader = new PipedReader();
            // 将输出流和输入流链接
            out.connect(reader);


            Thread thread = new Thread(new Print(reader), "PrintThread");
            thread.start();


            try {
                int receive = 0;
                while ((receive = System.in.read()) != -1) {
                    out.write(receive);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                out.close();
                reader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Print implements Runnable {

        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
