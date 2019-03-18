package com.std.video.geyiming.tec7.future;

/**
 * Future模式演示
 *
 * @author zhaojy
 * @date 2017-12-28
 */
public class RealData implements Data {
    public String result;

    public RealData(String para) {
        // RealData的构造可能很慢，需要用户等待很久，这里使用sleep模拟
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(para);
            try {
                // 使用sleep，模拟一个很慢的操作过程
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.result = stringBuffer.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
