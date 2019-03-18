package com.std.video.geyiming.tec7.future;

/**
 * Future模式演示
 *
 * @author zhaojy
 * @date 2017-12-28
 */
public class FutureData implements Data {
    protected RealData realData = null; // FutureData是realData的包装
    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        // todo 此处有问题，无法进入notifyAll
        if (!isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();    // RealData已经被注入，通知getResult()
    }

    /**
     * @return String
     */
    @Override
    public synchronized String getResult() {    // 会等待RealData构造完成
        while (!isReady) {  // todo  此处能读取到被修改的isReady?,线程中是无法读取的。
            try {
                wait(); // 一直等待，直到RealData被注入
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return realData.result;    // 有RealData实现
    }
}
