package com.std.video.geyiming.tec7.future;

import java.util.concurrent.Callable;

/**
 * @author zhaojy
 * @date 2017-12-28
 */
public class RealDataNew implements Callable<String> {
    private String para;

    public RealDataNew(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
