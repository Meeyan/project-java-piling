package com.std.arithmetic;

import cn.hutool.core.util.ObjectUtil;

/**
 * 二叉树
 *
 * @author zhaojy
 * @date 2020/5/11 6:11 PM
 */
public class BinaryTree {


    /**
     * 中序遍历二叉树
     *
     * @param node Node
     */
    public void inorderTreeWalk(Node node) {
        if (node != null) {

            inorderTreeWalk(node.left);

            System.out.println(node.value);

            inorderTreeWalk(node.right);
        }
    }


    /**
     * 搜索节点
     * - 从树根开始递归期间遇到的节点就形成了一条向下的简单路径，所以treeSearch的运行时间为 O(h)，
     * 其中h是这棵树的高度。
     * - 采用while比递归高效，为什么？
     *
     * @param node  Node
     * @param value int
     * @return Node
     */
    public Node treeSearch(Node node, int value) {
        if (ObjectUtil.isNull(node) || node.value == value) {
            return node;
        }
        if (value < node.value) {
            return treeSearch(node.left, value);
        } else {
            return treeSearch(node.right, value);
        }
    }

    /**
     * 迭代搜索树
     *
     * @param node  Node
     * @param value int
     * @return
     */
    public Node iterativeTreeSearch(Node node, int value) {
        while (node != null && value != node.value) {
            if (value < node.value) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    /**
     * 查找树的最小节点
     * 耗时：O(h) ，h为树高
     *
     * @param node Node
     * @return Node
     */
    public Node findTreeMinimum(Node node) {
        while (ObjectUtil.isNotNull(node) && ObjectUtil.isNotNull(node.left)) {
            node = node.left;
        }
        return node;
    }

    /**
     * 查找树中最大节点
     * 耗时：O(h) ，h为树高
     *
     * @param node Node
     * @return Node
     */
    public Node findTreeMaximum(Node node) {
        while (ObjectUtil.isNotNull(node) && ObjectUtil.isNotNull(node.right)) {
            node = node.right;
        }
        return node;
    }

    /**
     * 查找后继节点。
     * 后继节点：这个树转换成线性有序（升序）状态后，紧随其后的节点便是后继节点
     *
     * @param node Node
     * @return Node
     */
    public Node treeSuccessor(Node node) {
        if (ObjectUtil.isNotNull(node.right)) {
            return treeSuccessor(node.right);
        }

        Node parent = node.parent;

        while (ObjectUtil.isNotNull(parent) && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

}

class Node {
    /**
     * 父节点
     */
    public Node parent;

    /**
     * 左子树
     */
    public Node left;

    /**
     * 右子树
     */
    public Node right;

    /**
     * 值
     */
    public int value;
}