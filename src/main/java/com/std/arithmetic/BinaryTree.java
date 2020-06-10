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
            System.out.print(node.value + " ");
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
                System.out.print(node.value + " ");
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
     * 中序遍历二叉树 - 递归
     *
     * @param node Node
     */
    public void midOrderTreeWalkRecursive(Node node) {
        if (node != null) {

            midOrderTreeWalkRecursive(node.left);

            System.out.print(node.value + " ");

            midOrderTreeWalkRecursive(node.right);
        }
    }

    /**
     * 中序遍历 - 栈式操作
     *
     * @param root Node
     */
    public void midOrderTreeWalkStack(Node root) {
        Stack<Node> treeNodeStack = new Stack<>();
        Node node = root;
        while (node != null || !treeNodeStack.isEmpty()) {

            while (node != null) {
                treeNodeStack.push(node);
                node = node.left;
            }

            // 找到了左子树中没有子树的点，但是为 null
            // 此时 pop 栈顶节点，打印，然后找出栈元素的右子树
            if (!treeNodeStack.isEmpty()) {
                node = treeNodeStack.pop();
                System.out.print(node.value + " ");
                node = node.right;
            }
        }
    }

    /**
     * 后续遍历二叉树 - 递归
     *
     * @param node Node
     */
    public void postOrderTreeWalkRecursive(Node node) {
        if (node != null) {

            postOrderTreeWalkRecursive(node.left);

            postOrderTreeWalkRecursive(node.right);

            System.out.print(node.value + " ");
        }
    }

    /**
     * 后续遍历二叉树 - 栈式 - 方法一 <br />
     * <p>
     * 对任意节点p，将其入栈，然后沿着其左子树一直向下搜索，直到搜索到没有左子树的节点，此时该节点出现在栈顶，但是此时不能将其出栈 <br />
     * 访问，因为此节点的右子还未被访问。所以接下来按照同样的规则对其右子树进行相同的处理。当访问完其右子树时，该节点又出现在栈顶，此时<br />
     * 可以将其出栈并访问。这样就保证了正确的访问顺序。可以看出，在整个过程中，每个节点都两次出现在栈顶，只有第二次出现在栈顶时，才能访问它。<br />
     * 因此需要设置一个变量标识该节点是否是第一次出现在栈顶。<br />
     * <p>
     * 缺点：<br />
     * -- 1. 同一个元素会出现2次入栈和出栈，浪费空间，效率低。
     * -- 2. 步长较多
     *
     * @param root Node
     */
    public void postOrderTreeWalkStack(Node root) {
        Stack<Node> treeNodeStack = new Stack<>();
        Node node = root;
        Node tmpNode;
        while (node != null || !treeNodeStack.isEmpty()) {
            while (node != null) {
                treeNodeStack.push(node);
                node.isFirst = true;
                node = node.left;
            }

            if (!treeNodeStack.isEmpty()) {
                tmpNode = treeNodeStack.peek();
                treeNodeStack.pop();
                if (tmpNode.isFirst) {
                    tmpNode.isFirst = false;
                    treeNodeStack.push(tmpNode);
                    node = tmpNode.right;
                } else {
                    System.out.print(tmpNode.value + " ");
                    node = null;
                }
            }
        }
    }

    /**
     * 后续遍历二叉树 - 栈式 - 方法一 <br />
     * <p>
     * 1. 需要构建一个父节点
     * 2. 只有右子需要特殊处理，参考流程图
     * 参考链接：https://www.jianshu.com/p/456af5480cee
     *
     * @param root Node
     */
    public void postOrderTreeWalkStack_2(Node root) {
        Stack<Node> treeNodeStack = new Stack<>();
        Node node = root;

        // 右子访问后，避免再次入栈
        Node lastVisit = root;
        while (node != null || !treeNodeStack.isEmpty()) {
            while (node != null) {
                treeNodeStack.push(node);
                node = node.left;
            }

            // 看一下栈顶的节点
            node = treeNodeStack.peek();

            // 节点无左子，有右子，右子树继续入栈
            if (node != null && node.right != null && node.right != lastVisit) {
                node = node.right;
                continue;
            }

            // 进行到此处，则节点的左右子树均为 null

            /*
             * node 是父节点的左子，则node.parent一定存在，所以下面的 treeNodeStack.peek() 不会有问题
             */
            if (node != null) {
                lastVisit = node = treeNodeStack.pop();
                System.out.print(node.value + " ");
                if (node.parent != null) {
                    if (node == node.parent.left) {
                        node = treeNodeStack.peek().right;
                    } else {
                        node = null;
                    }
                } else {
                    node = null;
                }
            }
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
     * <p>后继节点：这个树转换成线性有序（升序）状态后，紧随其后的节点便是后继节点
     * <p>前驱节点：这个树转换成线性有序（升序）状态后，紧在其前的节点便是前驱节点
     *
     * @param node Node
     * @return Node
     */
    public Node treeSuccessor(Node node) {

        /**
         * 两种情况：
         * 1. node 有右子，则其后继一定在右子树。
         * 2. node 无右子，则表明 node 直接右子无后继节点，此时需要回溯，向上找最近的 有左子的父节点 pNode，则 pNode 就是 node 的后继。
         */

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

    /**
     * 查找前驱节点
     * <p> 前驱节点定义看上面
     *
     * @param node Node
     * @return Node
     */
    public Node treePredecessor(Node node) {

        if (ObjectUtil.isNotNull(node.left)) {
            return findTreeMaximum(node.left);
        }

        Node parent = node.parent;
        while (ObjectUtil.isNotNull(parent) && node == parent.left) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    /**
     * 构建一个树
     *
     * @param array int[]
     * @return Node
     */
    public Node buildBinaryTree(int[] array, int rootValue) {
        if (null == array || array.length <= 0) {
            return null;
        }
        Node root = new Node();
        root.value = rootValue;
        for (int value : array) {
            insertNode(root, value);
        }
        return root;
    }

    public void insertNode(Node node, int value) {
        if (value == node.value) {
            return;
        }
        if (value < node.value) {
            if (node.left == null) {
                Node left = new Node(value);
                node.left = left;
                left.parent = node;
            } else {
                insertNode(node.left, value);
            }
        } else {
            if (node.right == null) {
                Node right = new Node(value);
                node.right = right;
                right.parent = node;
            } else {
                insertNode(node.right, value);
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 8, 29, 10, 18, 23, 16, 33, 26, 21};
        BinaryTree binaryTree = new BinaryTree();
        Node node = binaryTree.buildBinaryTree(array, 1);

        System.out.print("\n先序-递归：");
        binaryTree.preOrderTreeWalkRecursive(node);

        System.out.print("\n先序-栈  ：");
        binaryTree.preOrderTreeWalkStack(node);
        System.out.print("\n中序-递归：");
        binaryTree.midOrderTreeWalkRecursive(node);
        System.out.print("\n中序-栈  ：");
        binaryTree.midOrderTreeWalkStack(node);

        System.out.print("\n后序-递归：");
        binaryTree.postOrderTreeWalkRecursive(node);

        System.out.print("\n后序-栈1 ：");
        binaryTree.postOrderTreeWalkStack(node);
        System.out.print("\n后序-栈2 ：");
        binaryTree.postOrderTreeWalkStack_2(node);
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

    public boolean isFirst;

    public Node(int value) {
        this.value = value;
    }

    public Node() {
    }
}
