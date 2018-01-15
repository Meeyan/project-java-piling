package com.std.video.geyiming.tec9;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * sdf并不是线程安全的，如果要增加线程安全的控制，需要对sdf进行包装处理，对parse代码块做同步处理。这样性能就会降低。
 *
 * @author zhajy
 * @create-time 2018-01-14
 */
public class D7_ThreadParseDate {
    // 为什么不把对象放到线程内部处理？
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                String time = "2018-01-14 17:00:" + (i % 60);
                System.out.println(time);
                Date parse = sdf.parse(time);
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
