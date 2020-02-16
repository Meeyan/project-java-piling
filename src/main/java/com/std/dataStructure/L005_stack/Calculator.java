package com.std.dataStructure.L005_stack;

/**
 * 栈模拟计算器
 *
 * @author zhaojy
 * @date 2020/2/6 1:11 PM
 */
public class Calculator {

    public static void main(String[] args) {
        // 根据思路，完成表达式计算
        String exp = "190+2*16/20";

        // 搞两个栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);

        // 定义需要大的变量
        // 用于扫描
        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;

        StringBuilder numberBuff = new StringBuilder();

        // 每次扫描得到的char保存到ch中
        char ch = ' ';
        while (true) {
            /**
             * todo 这种方式决定了，只能计算10以内的一个数字的处理
             * 解决方法：
             *   1. 使用正则表达式，将数字和符号完全匹配出来
             *   2. 在入数栈的地方，是数字，就向后多找几位，知道找到了一个符号
             */
            ch = exp.substring(index, index + 1).charAt(0);

            // 是否是运算符
            if (operStack.isOper(ch)) {

                // 符栈不为空，需要比较优先级
                if (!operStack.isEmpty()) {
                    /**
                     *  1. 如果当前的操作符的优先级小于或者等于栈中的操作符，就需要从数栈中pop出两个数，符号栈中pop出一个符号，进行运算，
                     *      得到结果，如数栈，当前符号入符号栈
                     */
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);

                        // 运算结果入数栈
                        numStack.push(res);

                        // 新的符号入符号栈
                        operStack.push(ch);
                    } else {
                        // 如果当前的操作符优先级大于栈中的操作符，直接入符号栈
                        operStack.push(ch);
                    }
                } else {
                    // 符号栈为空，则直接入符号栈
                    operStack.push(ch);
                }

                index++;
            } else {
                int tmpIndex = index;
                tmpIndex++;

                // 清空原有数据
                numberBuff.delete(0, numberBuff.length());

                // todo 此处使用的是char，根据ascii码，转成数字，需要减去48，即：'1' => 1
                numberBuff.append(ch - 48);
                while (true) {
                    if (tmpIndex >= exp.length()) {
                        break;
                    }
                    ch = exp.substring(tmpIndex, tmpIndex + 1).charAt(0);
                    if (operStack.isOper(ch)) {
                        break;
                    }
                    numberBuff.append(ch - 48);
                    tmpIndex++;
                }

                int val = Integer.parseInt(numberBuff.toString());
                numStack.push(val);
                index = tmpIndex;
            }

            if (index >= exp.length()) {
                break;
            }
        }


        // 当表达式扫描完毕，就顺序的从 数栈 和符号栈 中pop出相应的数和符号，并运行 todo 为什么要有这些？
        while (true) {
            if (operStack.isEmpty()) {
                break;
            }

            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);
        }

        res = numStack.pop();
        System.out.printf("表达式:%s = %d", exp, res);
    }

}


class ArrayStack2 {
    private int maxSize;

    private int[] stack;

    private int top = -1;

    public ArrayStack2(int maxSize) {
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

    /**
     * 计算运算符的优先级
     *
     * @param oper
     * @return
     */
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            // 假定表达式只有加减乘除
            return -1;
        }
    }

    /**
     * 操作符是否有效
     *
     * @param val
     * @return
     */
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    /**
     * 计算结果
     *
     * @param num1 int
     * @param num2 int
     * @param oper int
     * @return int
     */
    public int cal(int num1, int num2, int oper) {
        int rest = 0;
        switch (oper) {
            case '+':
                rest = num1 + num2;
                break;
            case '-':
                rest = num2 - num1;
                break;
            case '*':
                rest = num1 * num2;
                break;
            case '/':
                rest = num2 / num1;
                break;
        }
        return rest;
    }

    /**
     * 返回栈定元素，但是不出栈
     *
     * @return int
     */
    public int peek() {
        return stack[top];
    }
}