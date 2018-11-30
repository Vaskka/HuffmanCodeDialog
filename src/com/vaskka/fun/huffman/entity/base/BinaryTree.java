package com.vaskka.fun.huffman.entity.base;

/**
 * @program: HuffmanCodeTree
 * @description: BinaryTree 二叉树
 * @author: Vaskka
 * @create: 2018/11/28 5:04 PM
 **/

public class BinaryTree {

    /**
     * 根节点
     */
    private Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }


}
