package com.std.dataStructure.L004_linkedList;

import java.util.Stack;

/**
 * 无序链表
 *
 * @author zhaojy
 * @date 2020/1/29 7:14 PM
 */
public class SingleLinkList {

    /**
     * 先初始化一个头节点
     */
    private HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

    public void setHead(HeroNode head) {
        this.head = head;
    }

    /**
     * 添加节点
     * 不考虑编号顺序时：
     * 1. 找到last节点
     * 2. 将最后这个节点的next 指向 新的节点
     *
     * @param heroNode HeroNode
     */
    public void add(HeroNode heroNode) {
        HeroNode tmpH = head;

        // 先遍历，找到尾节点
        while (true) {
            if (tmpH.next == null) {
                break;
            }
            tmpH = tmpH.next;
        }
        tmpH.next = heroNode;
    }


    /**
     * 有序添加
     * 思路：
     * 1. 按照从小到大的顺序排列
     * 2. 下一个node的no比当前node的no大，则视为找到
     * 3. 下一个node的no和当前node的no相同，视为重复，提示错误
     * 4. 新的node的next节点指向下一个node，上一个节点的next节点指向新的node，插入完成
     *
     * @param node HeroNode
     */
    public void addByOrder(HeroNode node) {
        HeroNode temp = this.head;

        boolean flag = false;

        while (true) {
            // 找到了链表尾部 或者 空链表
            if (temp.next == null) {
                flag = true;
                break;
            }
            // 找到位置
            if (temp.next.getNo() > node.getNo()) {
                flag = true;
                break;
            } else if (temp.next.getNo() == node.getNo()) {
                // 相同，提示重复
                break;
            }
            temp = temp.next;
        }

        // 找到位置后，插入新的元素
        if (flag) {
            node.next = temp.next;
            temp.next = node;
        }
    }

    /**
     * 修改链表
     *
     * @param newHeroNode HeroNode
     */
    public void update(HeroNode newHeroNode) {

        if (head.next == null || newHeroNode == null) {
            System.out.println("链表为空或者传入的节点为空");
            return;
        }
        HeroNode temp = head.next;
        while (true) {
            // 找到了链表的末尾
            if (temp == null) {
                break;
            }

            // 找到了节点，设置新值
            if (temp.getNo() == newHeroNode.getNo()) {
                temp.setName(newHeroNode.getName());
                temp.setNickName(newHeroNode.getNickName());
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 删除链表中的某个节点
     * 思路：
     * 1. 找到链表中被删除节点的父节点
     * 2. 将父节点的next指向被删除节点的next节点。
     *
     * @param no int
     */
    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        // 必须要从head开始
        HeroNode temp = head;

        while (true) {
            if (temp.next == null) {
                System.out.println("没有找到被删除的节点");
                break;
            }

            // 找到被删除节点的父节点
            if (temp.next.getNo() == no) {
                HeroNode old = temp.next;
                temp.next = temp.next.next;
                // 清除原有节点
                old.next = null;
                break;
            }

            temp = temp.next;
        }
    }

    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode headNext = head.next;
        while (true) {
            if (headNext == null) {
                break;
            }
            System.out.println(headNext);

            // temp后移
            headNext = headNext.next;
        }
    }

    /**
     * 求单链表的长度
     *
     * @param head HeroNode
     * @return int
     */
    public static int getLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        HeroNode current = head.next;
        int length = 0;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }


    /**
     * 查找单链表中的倒数第k个节点（新浪面试题）
     * 思路：
     * 1. 先搞到链表的长度（节点个数）
     * 2. 用size - index，定位到了节点。
     *
     * @param head  HeroNode 头节点
     * @param index int 倒数第几个位置
     * @return HeroNode
     */
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        if (head.next == null) {
            return null;
        }

        // 第一次遍历
        int size = getLength(head);

        // 第二次遍历，size - index 位置，就是我们倒数的第k个节点。
        // 部分判断可以前置，减少无效遍历
        if (index <= 0 || size < index) {
            return null;
        }

        HeroNode curr = head.next;
        for (int i = 0; i < (size - index); i++) {
            curr = curr.next;
        }
        return curr;
    }

    /**
     * 单链表反转
     *
     * @param head HeroNode
     */
    public static void reverseList(HeroNode head) {
        if (head.next == null || head.next.next == null) {
            return;
        }
        HeroNode curr = head.next;

        // 指向当前节点[curr]的下一个节点
        HeroNode next = null;

        // 反转后的链表的头节点
        HeroNode reverseHead = new HeroNode(0, "", "");

        while (curr != null) {

            next = curr.next;

            // 将已经反转的链表赋给curr.next
            curr.next = reverseHead.next;

            // 反转的头节点执行curr，完成curr加入链表：：这一步是关键
            reverseHead.next = curr;

            // curr移步到下一个未反转的节点
            curr = next;
        }

        // 旧的链表头指向反转后的数据
        head.next = reverseHead.next;
    }

    /**
     * 链表，从尾部开始打印
     *
     * @param head HeroNode
     */
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return;
        }

        HeroNode curr = head.next;

        // 使用栈完成
        Stack<HeroNode> stack = new Stack<>();

        while (curr != null) {
            stack.push(curr);
            curr = curr.next;
        }

        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }

    public static void main(String[] args) {
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode node2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode node3 = new HeroNode(3, "吴用", "智多星");

        SingleLinkList linkList = new SingleLinkList();
        linkList.addByOrder(node1);
        linkList.addByOrder(node3);
        linkList.addByOrder(node4);
        linkList.addByOrder(node2);

        linkList.list();

        System.out.println("修改后的链表数据");

        HeroNode nodeUpdate = new HeroNode(2, "卢俊义和", "玉麒麟~~");
        linkList.update(nodeUpdate);

        linkList.delete(1);

        linkList.list();

        System.out.println(getLength(linkList.getHead()));

        System.out.println("找到的节点：" + findLastIndexNode(linkList.getHead(), 2));

        // reverseList(linkList.getHead());

        System.out.println("反转后--");
        linkList.list();

        System.out.println("从尾部开始打印---");
        reversePrint(linkList.getHead());

    }
}


class HeroNode {

    private int no;

    private String name;

    private String nickName;

    public HeroNode next;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public HeroNode getNext() {
        return next;
    }

    public void setNext(HeroNode next) {
        this.next = next;
    }

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}