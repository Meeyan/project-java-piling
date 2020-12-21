package com.demo.thread;

/**
 * @author zhaojy
 * @date 2020/12/21 5:14 下午
 */
public
class WorkThread implements Runnable {

    private String command;

    public WorkThread(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start. command :" + command);
        processCommand();
        System.out.println(Thread.currentThread().getName() + " end");
    }

    private void processCommand() {
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "WorkThread{" +
                "command='" + command + '\'' +
                '}';
    }
}
