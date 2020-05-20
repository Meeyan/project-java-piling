package com.std.arithmetic;

import cn.hutool.core.util.ObjectUtil;

import java.util.Stack;

/**
 * 二叉树
 *
 * @author zhaojy
 * @date 2020/5/11 6:11 PM
 */
public class BinaryTree {


    /**
     * 先序遍历二叉树 - 递归
     *
     * @param node Node
     */
    public void preOrderTreeWalkRecursive(Node node) {
        if (ObjectUtil.isNotNull(node)) {
            System.out.println(node.value);
            preOrderTreeWalkRecursive(node.left);
            preOrderTreeWalkRecursive(node.right);
        }
    }

    /**
     * 先序遍历二叉树 - 栈
     *
     * @param root Node
     */
    public void preOrderTreeWalkStack(Node root) {
        Stack<Node> treeNodeStack = new Stack<>();
        Node node = root;
        while (node != null || !treeNodeStack.isEmpty()) {
            // 处理左子树，并入栈
            while (node != null) {
                System.out.println(node.value + " ");
                treeNodeStack.push(node);
                node = node.left;
            }
            // 处理完左子树后，回溯，搞到left.parent.right，继续遍历
            if (!treeNodeStack.isEmpty()) {
                node = treeNodeStack.pop();
                node = node.right;
            }
        }
    }


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
     * @return Node
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
     * 前驱节点：这个树转换成线性有序（升序）状态后，紧在其前的节点便是前驱节点
     *
     * @param node Node
     * @return Node
     */
    public Node treeSuccessor(Node node) {

        // 如果存在右子树，则查找右子树中的最小节点，则该节点就是后继节点
        if (ObjectUtil.isNotNull(node.right)) {
            return findTreeMinimum(node.right);
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
