package com.std.video.geyiming.tec9;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用ThreadLocal对SimpleDateFormat进行包装处理，<b>为每一个线程分配一个实例</b>
 *
 * @author zhaojy
 * @create-time 2018-01-14
 */
public class D7_ThreadLocalParseDate {

    // 使用ThreadLocal包装局部变量
    static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<>();
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class ParseDate implements Runnable {

        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            if (tl.get() == null) {
                tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                // tl.set(sdf); // ThreadLocal只维护局部变量
            }
            try {
                Date parse = tl.get().parse("2018-01-14 17:10:" + i % 60);
                System.out.println(i + ":" + parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService esc = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            esc.execute(new ParseDate(i));
        }
    }
}
