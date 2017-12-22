package com.std.video.geyiming.tec4;

/**
 * 测试
 */
public class TestLockFreeVector {
    public static void main(String[] args) {
        final LockFreeVector<PObject> lockFreeVector = new LockFreeVector<>();
        for (int i = 0; i < 1; i++) {
            final int m = i;
            new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        PObject po = new PObject(m + "" + j, "name:" + j);
                        lockFreeVector.push_back(po);
                    }
                }
            }.start();
        }
    }

}

class PObject {
    String id;
    String name;

    public PObject(String id, String name) {
        this.id = id;
        this.name = name;
    }
}