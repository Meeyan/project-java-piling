package com.util;

/**
 * id生成器
 * <p>雪花算法
 * <p>参考链接：https://segmentfault.com/a/1190000011282426
 *
 * @author zhaojy
 * @date 2020/6/8 4:07 PM
 */
public class IdGenerator {

    /**
     * 下面两个每个5位，加起来就是10位的工作机器id
     */

    /**
     * 工作id
     */
    private long workerId;
    /**
     * 数据id
     */
    private long dataCenterId;

    /**
     * 12位的序列号
     */
    private long sequence;

    /**
     * @param workerId     long 节点id
     * @param dataCenterId long 机房id
     * @param sequence     long 序列号
     */
    public IdGenerator(long workerId, long dataCenterId, long sequence) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }

        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }

        System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
                timestampLeftShift, dataCenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.sequence = sequence;
    }

    /**
     * 初始时间戳，用于用当前时间戳减去这个时间戳，算出偏移量
     * 2018/1/1 00:00:00
     */
    private long twepoch = 1514736000000L;

    /**
     * workerId占用的位数：5
     */
    private long workerIdBits = 5L;

    /**
     * dataCenterIdBits占用的位数：5
     */
    private long dataCenterIdBits = 5L;

    /**
     * workerId 可以使用的最大数值：31
     */
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * dataCenterId 可以使用的最大数值：31
     */
    private long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * 序列号占用的位数：12
     */
    private long sequenceBits = 12L;

    /**
     * 序列号最大值
     */
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作id需要左移的位数，12位
     */
    private long workerIdShift = sequenceBits;
    /**
     * 数据id需要左移位数 12+5=17位
     */
    private long dataCenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间戳需要左移位数 12+5+5=22位
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 上次时间戳，初始值为负数
     */
    private long lastTimestamp = -1L;


    /**
     * 下一个ID生成算法
     *
     * @return long
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        /**
         * 同一毫秒内：
         *  - 获取当前时间戳如果等于上次时间戳（同一毫秒内），则在序列号加一；
         *  - 否则序列号赋值为0，从0开始。
         */
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        //将上次时间戳值刷新
        lastTimestamp = timestamp;

        /**
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (dataCenterId << dataCenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        return ((timestamp - twepoch) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 获取时间戳，并与上次时间戳比较
     *
     * @param lastTimestamp long
     * @return long
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取系统时间戳
     *
     * @return long
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        IdGenerator worker = new IdGenerator(1, 1, 1);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            System.out.println(worker.nextId());
        }
        System.out.println();

        System.out.println("耗时" + (System.currentTimeMillis() - startTime));
    }

}
