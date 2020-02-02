package com.std.dataStructure.L004_linkedList;

/**
 * Josephu问题处理<p>
 * 描述如下：<p>
 * 设编号为1，2，...n的n个人围坐一圈，约定编号为k（1<= k <= n）的人，从1开始报数，<p>
 * 数到m的那个人出列，它的下一位又从1开始报数，数到m的那个人又出列，一次类推，知道所<p>
 * 有人出列为止，由此产生一个出队编号的序列。<p>
 *
 * @author zhaojy
 * @date 2020/1/31 4:44 PM
 */
public class Josephu {

    public static void main(String[] args) {
        CircleSignalLinkedList linkedList = new CircleSignalLinkedList();
        linkedList.add(5);
        linkedList.showBoy();

        linkedList.countBoy(1, 2, 5);
    }
}

/**
 * 环形，单向链表
 */
class CircleSignalLinkedList {


    private Boy first;

    /**
     * 构建一个环形链表
     *
     * @param num int
     */
    public void add(int num) {
        if (num < 1) {
            System.out.println("num数值不正确");
            return;
        }

        Boy curr = null;
        for (int i = 1; i <= num; i++) {
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                first.setNext(first);
                curr = first;
            } else {
                // 新建节点的next指向first，完成环
                boy.setNext(first);

                // 把新建节点加入链表
                curr.setNext(boy);

                // 当前指针后移
                curr = boy;
            }
        }
    }

    /**
     * 打印环形链表
     */
    public void showBoy() {
        if (first == null) {
            System.out.println("没有任何小孩");
            return;

        }

        // first不能动，所以从第一个开始遍历
        Boy curBoy = first;

        while (true) {
            System.out.printf("小孩的编号:%d \n", curBoy.getNo());
            // 说明已经遍历完毕，终止打印
            if (curBoy.getNext() == first) {
                break;
            }
            // 指针后移
            curBoy = curBoy.getNext();
        }
    }

    /**
     * 小孩出圈
     *
     * @param startNo  int 从第几个小孩开始数
     * @param countNum int 数几下出圈
     * @param nums     int 总数多少
     */
    public void countBoy(int startNo, int countNum, int nums) {
        /**
         * 思路分析：
         *  1. 由于使用的是单向链表，所以需要一个辅助节点，用来定位出圈节点的父节点
         *  2. 当一个节点出圈时，父节点指向出圈节点的next节点。
         *  3. 当链表中只有一个节点时，完成全部操作。
         */
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数输入有误，清重新输入");
            return;
        }

        // 创建辅助指针，帮助完成小孩出圈
        Boy parent = first;

        while (true) {
            // helper 已经指向最后的小孩节点
            if (parent.getNext() == first) {
                break;
            }
            parent = parent.getNext();
        }

        // 从那个小孩开始数，需要定位到具体的小孩
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            parent = parent.getNext();
        }

        // 出圈小孩
        Boy outBoy = first;

        // 当小孩报数时，让first和helper指针同时移动m-1次，然后出圈
        while (true) {

            // 说明圈中只有一个节点
            if (parent == outBoy) {
                break;
            }

            for (int i = 0; i < countNum - 1; i++) {
                outBoy = outBoy.getNext();
                parent = parent.getNext();
            }

            System.out.printf("小孩:%d 出圈\n", outBoy.getNo());

            // 移除出圈小孩
            outBoy = outBoy.getNext();
            parent.setNext(outBoy);
        }

        System.out.printf("最后留在圈中的小孩编号:%d\n", outBoy.getNo());
    }

}

class Boy {

    private int no;
    private Boy next;

    public Boy(int i) {
        this.no = i;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
