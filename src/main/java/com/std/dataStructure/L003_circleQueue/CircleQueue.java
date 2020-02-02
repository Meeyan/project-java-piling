package com.std.dataStructure.L003_circleQueue;

import java.util.Scanner;

/**
 * 数组-环形队列<p>
 * 核心思想：取模
 *
 * @author zhaojy
 * @date 2020/1/26 4:20 PM
 */
public class CircleQueue {

    /**
     * 数组最大容量
     */
    private int maxSize;

    /**
     * 队列头，指向头数据。front就指向队列的第一个元素，也就是说arr[front]就是队列的第一个元素
     * 默认0
     */
    private int front;

    /**
     * 队列尾，rear指向队列的最后一个元素的最后一个位置
     * 默认0
     */
    private int rear;

    /**
     * 存储数据
     */
    private int[] arr;

    public CircleQueue(int arrayMaxSize) {
        maxSize = arrayMaxSize;
        arr = new int[maxSize];
    }

    public boolean isFull() {
        // 当队列被重用时，比如maxSize=5，front=3，rear=2，此时一定经历了出队操作，
        // 产生了复用，(2+1)%5=3，标识队列已满（个人理解，会留有一个空位）。
        return (rear + 1) % maxSize == front;
    }

    public boolean isEmpty() {
        // todo 这个方式有问题吧？
        return rear == front;
    }

    public void addQueue(int data) {
        if (isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }

        arr[rear] = data;
        // 将rear后移，考虑取模，如果rear到了最后，则要考虑重用，环形开始。
        rear = (rear + 1) % maxSize;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能取数据");
        }

        // front指向队列的第一个元素
        // 1. 先把front对应的值保留到一个临时变量
        // 2. 将front后移，考虑取模
        // 3. 将临时保存的变量返回。

        int retVal = arr[front];
        front = (front + 1) % maxSize;
        return retVal;
    }

    /**
     * 显示队列的数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列空的，没有数据");
            return;
        }

        // 从front开始遍历，遍历多少个元素。
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    /**
     * 求出当前队列有效的数据个数
     */
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }


    /**
     * 显示队列头部数据
     */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能取数据");
        }
        return arr[front];
    }

    public static void main(String[] args) {
        CircleQueue queue = new CircleQueue(4);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("[----s(show):显示队列]");
            System.out.println("[----e(exit):退出程序]");
            System.out.println("[----a(add):添加数据到队列]");
            System.out.println("[----g(get):从队列获取数据]");
            System.out.println("[----h(head):查看队列头的数据]");
            key = scanner.next().charAt(0);

            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数！");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    int res = queue.getQueue();
                    System.out.printf("取出的数据是：%d\n", res);
                    break;
                case 'h':
                    int head = queue.headQueue();
                    System.out.printf("头数据是：%d\n", head);
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }

        }
        System.out.println("程序退出");
    }

}
