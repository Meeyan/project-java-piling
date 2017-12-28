package com.std.video.geyiming.tec7.future;

/**
 * Future模式演示
 *
 * @author zhaojy
 * @create-time 2017-12-28
 */
public class Client {

    public Data request(final String queryStr) {
        final FutureData futureData = new FutureData();
        new Thread() {
            @Override
            public void run() { // RealData的构建很慢
                // 所以在单独的线程中运行
                RealData data = new RealData(queryStr);
                futureData.setRealData(data);
            }
        }.start();
        return futureData;  // FutureData会被立即返回
    }
}
