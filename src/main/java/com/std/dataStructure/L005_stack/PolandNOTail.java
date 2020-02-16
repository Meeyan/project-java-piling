package com.std.dataStructure.L005_stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰表达式
 *
 * @author zhaojy
 * @date 2020/2/9 12:44 PM
 */
public class PolandNOTail {
    public static void main(String[] args) {

        // 先定义一个逆波兰表达式，为了说明方便，使用空格隔开
        // 中缀表达式：4 * 5 - 8 + 60 + 8/2 转换为后缀： 4 5 * 8 - 60 + 8 2 / +
        String exp = "4 5 * 8 - 60 + 8 2 / +";

        List<String> listString = getListString(exp);
        System.out.println("计算结果：" + calculate(listString));


        /**
         * 1. 目的：1+((2+3)x4)-5 => 1 2 3 + 4 x + 5 -
         * 2. 直接操作str不方便，因此先将"1+((2+3)x4)-5" => 转成中缀表达式对应的List
         *      即："1+((2+3)x4)-5" => ArrayList[1,+,(,(,2,+,3,),x,4),-,5]
         *
         * 3. 将得到的中缀表达式对应的List => 后缀表达式List
         */
        String midStr = "1+((2+3)*9)-5";

        List<String> midExpList = toMidExpList(midStr);
        System.out.println("转换的中缀表达式List为：" + midExpList);

        List<String> tailList = midListToTailList(midExpList);
        System.out.println("中缀表达式转换为后缀表达式结果：" + midListToTailList(midExpList)); // [1, 2, 3, +, 4, *, +, 5, -]

        System.out.println("表达式的结果：" + calculate(tailList));

    }

    /**
     * 中缀表达式List => 后缀表达式List
     */
    public static List<String> midListToTailList(List<String> midList) {

        // 符号栈
        Stack<String> s1 = new Stack<>();

        // 存储中间件的结果栈，在整个转换过程中，没有pop操作，而且后面我们还需要逆序输出
        // 因此比较麻烦，这里我们白不用Stack，而是使用List代替栈。
        // Stack<String> s2 = new Stack<>();

        // 存储中间结果的List2
        List<String> s2 = new ArrayList<>();

        for (String item : midList) {
            // if item is a number,then put item in s2
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equalsIgnoreCase("(")) {
                // if item represents left bracket,then put it in s1
                s1.push(item);
            } else if (item.equalsIgnoreCase(")")) {
                // if the item represents right bracket,then pop the top element one by one from s1 until meet the left bracket,
                // and put them in s2，drop the left bracket and right bracket finally.
                // 如果是右括号")"，则依次弹出s1栈顶的运算符，并压入s2，知道遇到左括号为止，此时将这一对括号丢弃。
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop(); /// ！！！！ 将左括号丢弃，消除左括号
            } else {
                // 当item的优先级小于等于s1栈顶运算符，将s1栈顶的愿算福弹出并加入到s2中，再次转到4.1与s1中新的栈顶运算符相比较
                // 问题：我们缺少一个比较运算符优先级的方法
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }

                // 还需要将item压入栈
                s1.push(item);
            }
        }

        // 将s1中剩余的运算符依次弹出压入s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }

        // 结果即是想要的结果
        return s2;
    }

    /**
     * 中缀表达式转成中缀表达式对应的list
     */
    public static List<String> toMidExpList(String str) {
        // 存储中缀表达式 对应的内容
        List<String> midList = new ArrayList<>();

        int index = 0;

        // 对多位数拼接
        String tmpStr;

        // 遍历的每一个字符
        char c;
        do {
            // ASCII码对应关系
            // 0 => 48
            // 9 => 57

            // 是符号
            if ((c = str.charAt(index)) < 48 || (c = str.charAt(index)) > 57) {
                midList.add("" + c);
                index++;
            } else {
                // 是数字，则多查几个
                // 置空
                tmpStr = "";
                while (index < str.length() && ((c = str.charAt(index)) >= 48 && (c = str.charAt(index)) <= 57)) {
                    // 拼接
                    tmpStr += c;
                    index++;
                }
                midList.add(tmpStr);
            }
        } while (index < str.length());

        return midList;
    }

    public static List<String> getListString(String exp) {
        String[] split = exp.split(" ");
        List<String> list = new ArrayList<>();
        for (String ele : split) {
            list.add(ele);
        }
        return list;
    }

    /**
     * 计算
     *
     * @param list List<String>
     * @return int
     */
    public static int calculate(List<String> list) {

        // 需要一个栈
        Stack<String> stack = new Stack<>();
        for (String ele : list) {
            // 数字直接入栈
            if (ele.matches("\\d+")) {
                stack.push(ele);
            } else {
                // 符号直接计算
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res;
                if (ele.equalsIgnoreCase("+")) {
                    res = num1 + num2;
                } else if (ele.equalsIgnoreCase("-")) {
                    res = num1 - num2;
                } else if (ele.equalsIgnoreCase("*")) {
                    res = num1 * num2;
                } else if (ele.equalsIgnoreCase("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                stack.push("" + res);
            }
        }

        // 最后留在stack中得数据就是最终结果
        return Integer.parseInt(stack.pop());
    }
}

/**
 * 比较运算符的优先级
 */
class Operation {

    /**
     * +
     */
    private static int ADD = 1;

    /**
     * -
     */
    private static int SUB = 1;

    /**
     * *
     */
    private static int MUL = 2;

    /**
     * /
     */
    private static int DIV = 2;

    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }
        return result;
    }


}
