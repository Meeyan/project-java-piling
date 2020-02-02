package com.std.dataStructure.L005_stack;

import java.util.Scanner;

/**
 * @author zhaojy
 * @date 2020/2/1 10:11 AM
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("show(s):显示栈");
            System.out.println("exit(e):退出程序");
            System.out.println("push(p)：入栈");
            System.out.println("pop(o):出栈");
            key = scanner.next();
            switch (key) {
                case "s":
                    stack.list();
                    break;
                case "p":
                    System.out.println("请输入一个数字:");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "o":
                    System.out.println("出栈的数据：" + stack.pop());
                    break;
                case "e":
                    scanner.close();
                    loop = false;
                    break;
            }
        }

        System.out.println("程序退出");
    }
}

class ArrayStack {
    private int maxSize;

    private int[] stack;

    private int top = -1;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[this.maxSize];
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int data) {
        if (isFull()) {
            System.out.println("栈满");
            return;
        }

        top++;
        stack[top] = data;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，没有数据");
        }

        int value = stack[top];
        top--;
        return value;
    }

    /**
     * 遍历时，需要从栈顶显示数据
     */
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }

        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }
}