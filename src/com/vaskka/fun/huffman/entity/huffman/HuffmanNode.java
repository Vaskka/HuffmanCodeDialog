package com.vaskka.fun.huffman.entity.huffman;

import com.vaskka.fun.huffman.entity.base.Node;

/**
 * @program: HuffmanCodeTree
 * @description: HuffmanNode 哈夫曼节点
 * @author: Vaskka
 * @create: 2018/11/28 5:49 PM
 **/

public class HuffmanNode extends Node {
    /**
     * 节点值
     */
    private Character value;

    /**
     * 权重
     */
    private int weight;


    public HuffmanNode(Node parent, Node leftChild, Node rightChild, Character value, int weight) {
        super(parent, leftChild, rightChild);
        this.value = value;
        this.weight = weight;
    }

    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }







    /**
     * 得到两个节点的权重和
     * @param n1 第一个节点
     * @param n2 d第二个节点
     * @return 权重和
     */
    public static int getSumWeight(HuffmanNode n1, HuffmanNode n2) {
        return n1.weight + n2.weight;
    }
}
