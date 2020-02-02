package com.std.dataStructure.L004_linkedList;

/**
 * 双向链表
 *
 * @author zhaojy
 * @date 2020/1/31 10:14 AM
 */
public class DoubleLinkedList {

    private HeroNode2 head = new HeroNode2(0, "", "");

    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode2 headNext = head.next;
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
     * 添加节点
     * 不考虑编号顺序时：
     * 1. 找到last节点
     * 2. 将最后这个节点的next 指向 新的节点
     *
     * @param heroNode HeroNode
     */
    public void add(HeroNode2 heroNode) {
        HeroNode2 tmpH = head;

        // 先遍历，找到尾节点
        while (true) {
            if (tmpH.next == null) {
                break;
            }
            tmpH = tmpH.next;
        }

        tmpH.next = heroNode;
        heroNode.pre = tmpH;
    }

    /**
     * 修改链表
     *
     * @param newHeroNode HeroNode
     */
    public void update(HeroNode2 newHeroNode) {

        if (head.next == null || newHeroNode == null) {
            System.out.println("链表为空或者传入的节点为空");
            return;
        }

        HeroNode2 temp = head.next;
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
     * 1. 找到链表中被删除节点
     * 2. 将该节点next的pre节点指向被删除节点的pre节点。
     * 3. 将pre节点的next节点指向该节点的next节点。
     *
     * @param no int
     */
    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        // 必须要从head开始
        HeroNode2 temp = head.next;

        while (true) {
            if (temp == null) {
                System.out.println("没有找到被删除的节点");
                break;
            }

            // 找到被删除节点的父节点
            if (temp.getNo() == no) {
                temp.pre.next = temp.next;

                // 尾节点
                if (temp.next != null) {
                    temp.next.pre = temp.pre;
                }

                // 清除原有节点，消除引用，帮助gc
                temp.next = null;
                temp.pre = null;
                break;
            }

            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        HeroNode2 node1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 node4 = new HeroNode2(4, "林冲", "豹子头");
        HeroNode2 node2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 node3 = new HeroNode2(3, "吴用", "智多星");

        DoubleLinkedList linkList = new DoubleLinkedList();
        linkList.add(node1);
        linkList.add(node3);
        linkList.add(node4);
        linkList.add(node2);

        linkList.list();

        HeroNode2 updateNode = new HeroNode2(4, "吴不用", "智多对星");
        linkList.update(updateNode);

        System.out.println("更新后的数据");
        linkList.list();


        linkList.delete(3);
        System.out.println("删除后的数据");
        linkList.list();
    }
}


class HeroNode2 {

    private int no;

    private String name;

    private String nickName;

    /**
     * 后一个节点
     */
    public HeroNode2 next;

    /**
     * 前一个节点
     */
    public HeroNode2 pre;

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

    public HeroNode2 getNext() {
        return next;
    }

    public void setNext(HeroNode2 next) {
        this.next = next;
    }

    public HeroNode2 getPre() {
        return pre;
    }

    public void setPre(HeroNode2 pre) {
        this.pre = pre;
    }

    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    /**
     * 不能打印节点信息，会引起链式打印
     *
     * @return String
     */
    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}