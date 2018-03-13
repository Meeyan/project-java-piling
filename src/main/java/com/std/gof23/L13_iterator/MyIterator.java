package com.std.gof23.L13_iterator;

/**
 * 迭代器
 *
 * @author zhaojy
 * @create-time 2018-03-13
 */
public interface MyIterator {
    void first();

    void next();

    Object getCurrentObj();

    boolean hasNext();

    boolean isFirst();

    boolean isLast();
}
