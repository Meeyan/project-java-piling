/*
 * Copyright (c) 2007 IBM Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.std.video.geyiming.tec4;

import java.util.AbstractList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * It is a thread safe and lock-free vector.
 * This class implement algorithm from:<br>
 * <p>
 * Lock-free Dynamically Resizable Arrays <br>
 * <p>
 * Damian Dechev, Peter Pirkelbauer, and Bjarne Stroustrup<br>
 * Texas A&M University College Station, TX 77843-3112<br>
 * {dechev, peter.pirkelbauer}@tamu.edu, bs@cs.tamu.edu<br>
 * <p>
 * <br>
 * 解读：<br>
 * 0. 用一个二维的AtomicReferenceArray<AtomicReferenceArray<E>>实现。<br>
 * 1. 外围的AtomicReferenceArray可以理解为一个篮子的集合【仓库】。<br>
 * 2. 篮子中最多放30个AtomicReferenceArray<E>【可以理解为一个篮子】，其中，第一个AtomicReferenceArray<E>存放8个元素，第二个16个，一次类推。<br>
 * 3. 每次新增的时候，不要修改原有的AtomicReferenceArray<E>，因为频繁的修改会浪费性能。
 *
 * @param <E> type of element in the vector
 * @author Zhi Gan
 */
public class LockFreeVector<E> extends AbstractList<E> {
    private static final boolean debug = false;
    /**
     * Size of the first bucket. sizeof(bucket[i+1])=2*sizeof(bucket[i])
     */
    private static final int FIRST_BUCKET_SIZE = 8;

    /**
     * number of buckets. 30 will allow 8*(2^30-1) elements
     */
    private static final int N_BUCKET = 30;

    /**
     * We will have at most N_BUCKET number of buckets. And we have
     * sizeof(buckets.get(i))=FIRST_BUCKET_SIZE**(i+1)
     */
    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;

    /**
     * 写描述器，一个该对象代表一个篮子。
     *
     * @param <E>
     * @author ganzhi
     */
    static class WriteDescriptor<E> {
        public E oldV;
        public E newV;
        public AtomicReferenceArray<E> addr;
        public int addr_ind;

        /**
         * Creating a new descriptorAtomicReference.
         *
         * @param addr     Operation address 哪一个篮子
         * @param addr_ind Index of address 篮子中的哪个对象。
         * @param oldV     old operand
         * @param newV     new operand
         */
        public WriteDescriptor(AtomicReferenceArray<E> addr, int addr_ind, E oldV, E newV) {
            this.addr = addr;
            this.addr_ind = addr_ind;
            this.oldV = oldV;
            this.newV = newV;
        }

        /**
         * set newV.
         */
        public void doIt() {
            addr.compareAndSet(addr_ind, oldV, newV);
        }
    }

    /**
     * 相当于篮子管理员，要知道仓库中有多少个篮子，每个篮子中有多少个元素。 <br/>
     * 即：仓库管理员知道他的仓库中有30个篮子，每个篮子中有多少苹果，以及总共有多少苹果。
     *
     * @param <E>
     * @author ganzhi
     */
    static class Descriptor<E> implements Comparable {
        public int size;    // 仓库中总共的Object数量
        volatile WriteDescriptor<E> writeop;

        /**
         * Create a new descriptorAtomicReference.
         *
         * @param size    Size of the vector
         * @param writeop Executor write operation
         */
        public Descriptor(int size, WriteDescriptor<E> writeop) {
            this.size = size;
            this.writeop = writeop;
        }

        /**
         *
         */
        public void completeWrite() {
            WriteDescriptor<E> tmpOp = writeop;
            if (tmpOp != null) {
                tmpOp.doIt();
                writeop = null; // this is safe since all write to writeop use
                // null as r_value.
            }
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public int compareTo(Object o) {
            System.out.println("equdkjs");
            return 0;
        }
    }

    // 相当于一个仓库管理员
    private AtomicReference<Descriptor<E>> descriptorAtomicReference;

    private static final int zeroNumFirst = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE);

    /**
     * Constructor.
     */
    public LockFreeVector() {
        // 初始化篮子集合【仓库】
        buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);

        // 给仓库中增加第一个篮子，篮子可以放8个对象
        buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));

        descriptorAtomicReference = new AtomicReference<Descriptor<E>>(new Descriptor<E>(0, null));
    }

    /**
     * add e at the end of vector.
     *
     * @param newElement element added
     */
    public void push_back(E newElement) {
        // 两个问题：
        //  1. 新增的元素准备增加到那个篮子里去？
        //  2. 新增的元素准备放到篮子中的第几个位置上？
        Descriptor<E> oldDescriptor;
        Descriptor<E> newDescriptor;
        do {
            oldDescriptor = descriptorAtomicReference.get();

            // 第一次调用：没有数据
            // 第n次写入：
            oldDescriptor.completeWrite();

            // 第一次增加数据时：desc.size为0，pos为篮子的容量，也就是8
            int pos = oldDescriptor.size + FIRST_BUCKET_SIZE;
            int zeroNumPos = Integer.numberOfLeadingZeros(pos);

            // 第一次：构造函数中初始化了第一个（0）
            // 第n次：
            int bucketInd = zeroNumFirst - zeroNumPos;  // 指代篮子的索引，也就是第几个篮子。

            // 从第二个篮子开始，新增篮子会触发。
            if (buckets.get(bucketInd) == null) {
                int newLen = 2 * buckets.get(bucketInd - 1).length();   // 拿到上一个篮子的大小，然后体积扩充一倍
                if (debug)
                    System.out.println("New Length is:" + newLen);
                buckets.compareAndSet(bucketInd, null, new AtomicReferenceArray<E>(newLen));
            }

            // 0x80000000 = -2147483648[int类型最小值]，二进制格式：10000000000000000000000000000000
            /*
             * 第一次：zeroNumPos为28，右移28位后，结果为8，做异或操作后，idx为0.
             */
            int idx = (0x80000000 >>> zeroNumPos) ^ pos;
            AtomicReferenceArray<E> bucket = buckets.get(bucketInd);    // 获取篮子
            newDescriptor = new Descriptor<E>(oldDescriptor.size + 1, new WriteDescriptor<E>(bucket, idx, null, newElement));
            if (oldDescriptor == newDescriptor) {
                System.out.println("true");
            }
        } while (!descriptorAtomicReference.compareAndSet(oldDescriptor, newDescriptor));   // 这个有用么？
        /*
         * 1. todo 为什么两个对象值不同还可以为true？
         *     理解出现了偏差，compareAndSet不是比对的oldDescriptor和newDescriptor，而是比对oldDescriptor和descriptorAtomicReference本身hold的对象是否一致，
         *  如果一致，则把本身的值替换为newDescriptor
         * 2. 有用。
         *    因为AtomicReference无法监视对象的过程变化，而每一次completeWrite都会将Descriptor中的WriteDescriptor清空，所以descriptorAtomicReference可以视为仅对size有感
         */
        descriptorAtomicReference.get().completeWrite();
        System.out.println(" do pop success. ");
    }

    /**
     * Remove the last element in the vector.
     *
     * @return element removed
     */
    public E pop_back() {
        Descriptor<E> desc;
        Descriptor<E> newd;
        E elem;
        do {
            desc = descriptorAtomicReference.get();
            desc.completeWrite();

            int pos = desc.size + FIRST_BUCKET_SIZE - 1;
            int bucketInd = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE) - Integer.numberOfLeadingZeros(pos);
            int idx = Integer.highestOneBit(pos) ^ pos;
            elem = buckets.get(bucketInd).get(idx);
            newd = new Descriptor<E>(desc.size - 1, null);
        } while (!descriptorAtomicReference.compareAndSet(desc, newd));

        return elem;
    }

    /**
     * Get element with the index.
     *
     * @param index index
     * @return element with the index
     */
    @Override
    public E get(int index) {
        int pos = index + FIRST_BUCKET_SIZE;
        int zeroNumPos = Integer.numberOfLeadingZeros(pos);
        int bucketInd = zeroNumFirst - zeroNumPos;
        int idx = (0x80000000 >>> zeroNumPos) ^ pos;
        return buckets.get(bucketInd).get(idx);
    }

    /**
     * Set the element with index to e.
     *
     * @param index index of element to be reset
     * @param e     element to set
     *              {@inheritDoc}
     */
    public E set(int index, E e) {
        int pos = index + FIRST_BUCKET_SIZE;
        int bucketInd = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE) - Integer.numberOfLeadingZeros(pos);
        int idx = Integer.highestOneBit(pos) ^ pos;
        AtomicReferenceArray<E> bucket = buckets.get(bucketInd);
        while (true) {
            E oldV = bucket.get(idx);
            if (bucket.compareAndSet(idx, oldV, e))
                return oldV;
        }
    }

    /**
     * reserve more space.
     *
     * @param newSize new size be reserved
     */
    public void reserve(int newSize) {
        int size = descriptorAtomicReference.get().size;
        int pos = size + FIRST_BUCKET_SIZE - 1;
        int i = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
                - Integer.numberOfLeadingZeros(pos);
        if (i < 1)
            i = 1;

        int initialSize = buckets.get(i - 1).length();
        while (i < Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE) - Integer.numberOfLeadingZeros(newSize + FIRST_BUCKET_SIZE - 1)) {
            i++;
            initialSize *= FIRST_BUCKET_SIZE;
            buckets.compareAndSet(i, null, new AtomicReferenceArray<E>(initialSize));
        }
    }

    /**
     * size of vector.
     *
     * @return size of vector
     */
    public int size() {
        return descriptorAtomicReference.get().size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E object) {
        push_back(object);
        return true;
    }
}
