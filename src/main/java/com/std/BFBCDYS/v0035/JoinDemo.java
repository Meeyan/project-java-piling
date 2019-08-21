package com.std.BFBCDYS.v0035;

/**
 * comment here
 *
 * @author zhaojy
 * @date 2019/8/22 0:07
 */
public class JoinDemo {
    public void a(Thread joinThread) {
        System.out.println("method a process - start");
        System.out.println(Thread.currentThread().getName());
        System.out.println(joinThread.getName());
        // start方法同样会获取锁，线程异步启动完成后，会立即释放锁，所以下面的join方法能立即拿到锁
        joinThread.start();
        try {
            /**
             * join原理：
             *  1. 程序是顺序执行的。
             *  2. start方法执行后，开启了异步线程
             *  3. join方法内部，判断thread.isAlive(),只要满足该方法（即joinThread.run()方法没有完成），
             *     都会wait，这会引起主线程等待join执行完毕
             *  4. 所以顺序控制成为可能
             */
            joinThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method a process - end");
    }

    public void b() {
        System.out.println("join 方法执行 - start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("join 方法执行 - end");

    }

    public static void main(String[] args) {
        JoinDemo joinDemo = new JoinDemo();

        Thread joinThread = new Thread(new Runnable() {
            @Override
            public void run() {
                joinDemo.b();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                joinDemo.a(joinThread);
            }
        }).start();
    }
}

