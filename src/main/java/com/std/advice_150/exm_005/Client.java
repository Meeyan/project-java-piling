package com.std.advice_150.exm_005;

public class Client {
    public void methodA(String str, Integer... is) {
    }

    public void methodA(String str, String... strs) {
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.methodA("China", 0);
        client.methodA("China", "people");

        // 方法模糊不清，编译器不知道调用那一个方法。另外，类型无法确认
        // 编码规则：KISS(Keep it simple,Stupid),懒人原则
        // client.methodA("China");
        // client.methodA("China", null);

        // 这种是可以的
        String strs[] = null;
        client.methodA("China", strs);
    }
}
