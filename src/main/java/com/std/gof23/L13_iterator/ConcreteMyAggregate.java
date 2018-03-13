package com.std.gof23.L13_iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合类
 *
 * @author zhaojy
 * @create-time 2018-03-13
 */
public class ConcreteMyAggregate {
    private List<Object> list = new ArrayList<>();

    public void addEle(Object object) {
        list.add(object);
    }

    public void remove(Object object) {
        list.remove(object);
    }

    public MyIterator getIterator() {
        return new ConcreteIterator();
    }

    /**
     * 迭代器
     */
    private class ConcreteIterator implements MyIterator {

        private int cursor;

        @Override
        public void first() {
            cursor = 0;
        }

        @Override
        public void next() {
            cursor++;
        }

        @Override
        public Object getCurrentObj() {
            return list.get(cursor);
        }

        @Override
        public boolean hasNext() {
            return cursor < list.size() ;
        }

        @Override
        public boolean isFirst() {
            return cursor == 0;
        }

        @Override
        public boolean isLast() {
            return cursor == (list.size() - 1);
        }
    }
}
