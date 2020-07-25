package com.std.deep_understand_jvm.zzm.ch02;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojy
 * @date 2020/4/19 17:36
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {

        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }
}
