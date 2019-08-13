package com.std.BFBCDYS.v0015;

/**
 * comment here
 *
 * @author zhaojy
 * @date 2019/8/13 23:02
 */
public class TestCase {
    public static void main(String[] args) {
        Tmall tmall = new Tmall();

        PushTarget pushTarget = new PushTarget(tmall);
        TakerTarget takerTarget = new TakerTarget(tmall);

        new Thread(pushTarget).start();
        new Thread(pushTarget).start();
        new Thread(pushTarget).start();
        new Thread(pushTarget).start();
        new Thread(pushTarget).start();

        new Thread(takerTarget).start();
        new Thread(takerTarget).start();
        new Thread(takerTarget).start();
        new Thread(takerTarget).start();
        new Thread(takerTarget).start();
        new Thread(takerTarget).start();

    }
}
