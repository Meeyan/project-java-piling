package com.std.video.geyiming.tec7.future;

/**
 * FutureData中的设计有问题，因为构造先于getResult运行，所以无法进入notifyAll
 *
 * @author zhaojy
 * @create-time 2017-12-28
 */
public class Run {
    public static void main(String[] args) {
        Client client = new Client();

        // 这里会立即返回，因为得到的是FutureData而不是RealData
        Data data = client.request("name");
        System.out.println("请求完毕..");
        try {
            // 这里可以用一个sleep代替了对其他业务逻辑的处理
            // 在处理这些业务逻辑的过程中，ReadData被创建，从而充分利用等待时间
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 使用真实的数据
        System.out.println("数据=" + data.getResult());
    }
}
