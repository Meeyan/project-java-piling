package com.std.BFBCDYS.chapter005;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用hashMap模拟缓存的实现
 *
 * @author zhaojy
 * @date 2019-07-01 20:19
 */
public class Cache {

    private static Map<String, Object> map = new HashMap<>();

    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private static Lock r = rwl.readLock();

    private static Lock w = rwl.writeLock();

    /**
     * 读取内容
     *
     * @param key String
     * @return Object
     */
    public static final Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    /**
     * 设置值
     * 设置新值，返回旧值
     *
     * @param key    String
     * @param object Object
     * @return Object
     */
    public static final Object put(String key, Object object) {
        w.lock();
        try {
            return map.put(key, object);
        } finally {
            w.unlock();
        }
    }

    /**
     * 清空内容
     */
    public static final void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }
}
