package com.std.dataStructure.L002_arrayQueue;

import java.util.Scanner;

/**
 * 数组队列<p>
 * todo 遗留问题：<p>
 * </span>1. 元素出队后，数据无法再次添加，即队列无法重复使用
 *
 * @author zhaojy
 * @date 2020/1/26 4:20 PM
 */
public class ArrayQueue {

    /**
     * 数组最大容量
     */
    private int maxSize;

    /**
     * 队列头，指向头的前一个数据
     */
    private int front;

    /**
     * 队列尾
     */
    private int rear;

    /**
     * 存储数据
     */
    private int[] arr;

    public ArrayQueue(int arrayMaxSize) {
        maxSize = arrayMaxSize;
        arr = new int[maxSize];

        // 初始时，队列头和队列尾指针都是-1
        front = -1;

        rear = -1;
    }

    public boolean isFull() {
        return rear == maxSize - 1;
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

        rear++;
        arr[rear] = data;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能取数据");
        }

        front++;
        return arr[front];
    }

    /**
     * 显示队列的数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列空的，没有数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    /**
     * 显示队列头部数据
     */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能取数据");
        }
        return arr[front + 1];
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(3);
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
