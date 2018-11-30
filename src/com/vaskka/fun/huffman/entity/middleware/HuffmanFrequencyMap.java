package com.vaskka.fun.huffman.entity.middleware;

import java.util.HashMap;

/**
 * @program: HuffmanCodeTree
 * @description: HuffmanMap 哈夫曼编码映射
 * @author: Vaskka
 * @create: 2018/11/28 5:17 PM
 **/

public class HuffmanFrequencyMap  {

    private HashMap<Character, Integer> innerMap;

    public HuffmanFrequencyMap() {
        innerMap = new HashMap<>();
    }

    public HuffmanFrequencyMap(HashMap<Character, Integer> innerMap) {
        this.innerMap = innerMap;
    }

    public HashMap<Character, Integer> getInnerMap() {
        return innerMap;
    }

    public void put(char c) {

        if (innerMap.containsKey(c)) {
            // 如果包含这个值，取出递增，再存回去
            int value = innerMap.get(c);
            value++;

            innerMap.put(c, value);
        }
        else {
            // 正常存储
            innerMap.put(c, 1);
        }
    }
}
