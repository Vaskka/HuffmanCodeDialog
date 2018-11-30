package com.vaskka.fun.huffman.entity.base;

/**
 * @program: HuffmanCodeTree
 * @description: Node 二叉树节点
 * @author: Vaskka
 * @create: 2018/11/28 5:02 PM
 **/

public class Node {

    /**
     * 父亲节点
     */
    private Node parent;

    /**
     * 左孩子
     */
    private Node leftChild;

    /**
     * 右孩子
     */
    private Node rightChild;


    public Node(Node parent, Node leftChild, Node rightChild) {
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * 判断节点是否是叶子结点
     * @return
     */
    public boolean isLeaf() {
        return getLeftChild() == null && getRightChild() == null;
    }


    /**
     * 得到自己是那一边的节点
     * @return 0==无父亲节点 -1==左子节点 1==右
     */
    public int getSide() {
        if (getParent() == null) {
            return 0;
        }
        else {
            if(getParent().getLeftChild().equals(this)) {
                return -1;
            }
            else {
                return 1;
            }
        }

    }


    /**
     * 得到某个节点的全部孩子数目
     * @param root
     * @return
     */
    public static int getChildrenNumber(Node root) {
        if (root.isLeaf()) {
            return 0;
        }

        if (root.getLeftChild() != null) {
            return getChildrenNumber(root.leftChild) + 1;
        }

        if (root.getRightChild() != null) {
            return getChildrenNumber(root.rightChild) + 1;
        }

        return 0;
    }

}
