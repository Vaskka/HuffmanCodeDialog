package com.vaskka.fun.huffman.core;

import com.vaskka.fun.huffman.entity.huffman.HuffmanNode;
import com.vaskka.fun.huffman.entity.huffman.HuffmanTree;
import com.vaskka.fun.huffman.entity.middleware.HuffmanFrequencyMap;
import com.vaskka.fun.huffman.entity.middleware.HuffmanMap;
import com.vaskka.fun.huffman.exceptions.*;
import com.vaskka.fun.huffman.utils.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: HuffmanCodeTree
 * @description: Compress 压缩类
 * @author: Vaskka
 * @create: 2018/11/30 8:05 PM
 **/

public class Compress {


    private static Compress compress;

    private Compress() {}

    /**
     * 获得压缩工具实例
     * @return Compress实例
     */
    public static Compress getInstance() {
        if (compress == null) {
            compress = new Compress();
        }

        return compress;
    }

    /**
     * 执行哈夫曼压缩，输出到源文件的同级目录
     * @param sourcePath 源文件目录
     * @throws IOException                  文件读写异常
     * @throws FileIsEmptyException         文件内容为空异常
     * @throws FileWriteFailedException     文件写入异常
     * @throws FileCreateFailedException    无法新建文件异常
     * @throws GetErrorCharException        非法字符异常
     * @throws GetErrorByteException        非法字节异常
     * @throws CharMappingNotFoundException 无法找到指定的字符映射异常
     */
    public void doHuffmanCompress(String sourcePath) throws IOException, FileIsEmptyException, FileWriteFailedException, FileCreateFailedException, GetErrorCharException, GetErrorByteException, CharMappingNotFoundException {

        String text = Util.readFile(sourcePath);

        // 得到Huffman编码表
        HuffmanMap huffmanMap = getHuffmanMap(text, sourcePath);

        // 输出编码json
        Util.writeFile(huffmanMap.toJsonString(), Util.getOutputJsonName(sourcePath));

        // 输出压缩文件
        writeCompressFileWithHuffmanMapping(text, huffmanMap, Util.getOutputCompressedFilePath(sourcePath));

    }

    /**
     * 执行哈夫曼压缩，输出到指定路径
     * @param sourcePath 源文件目录
     * @param outputPath 输出文件目录
     * @throws IOException                  文件读写异常
     * @throws FileIsEmptyException         文件内容为空异常
     * @throws FileWriteFailedException     文件写入异常
     * @throws FileCreateFailedException    无法新建文件异常
     * @throws GetErrorCharException        非法字符异常
     * @throws GetErrorByteException        非法字节异常
     * @throws CharMappingNotFoundException 无法找到指定的字符映射异常
     */
    public void doHuffmanCompress(String sourcePath, String outputPath) throws IOException, FileIsEmptyException, GetErrorCharException, GetErrorByteException, CharMappingNotFoundException, FileCreateFailedException, FileWriteFailedException {
        String text = Util.readFile(sourcePath);

        // 得到Huffman编码表
        HuffmanMap huffmanMap = getHuffmanMap(text, sourcePath);

        // 输出编码json
        Util.writeFile(huffmanMap.toJsonString(), Util.getOutputJsonName(outputPath));

        // 输出压缩文件
        writeCompressFileWithHuffmanMapping(text, huffmanMap, Util.getOutputCompressedFilePath(outputPath));

    }


    /**
     * 得到文件内容的Huffman编码表
     * @param text 文件内容
     * @param sourcePath 文件路径
     * @return HuffmanMap
     * @throws GetErrorCharException 出现非法字符异常
     * @throws FileIsEmptyException 文件内容为空异常
     */
    private HuffmanMap getHuffmanMap(String text, String sourcePath) throws GetErrorCharException, FileIsEmptyException {

        // 处理文件为空的异常
        if (text.equals("")) {
            throw new FileIsEmptyException(new File(sourcePath), "文件内容为空!");
        }

        // 得到字符频率表
        HuffmanFrequencyMap huffmanFrequencyMap = fromTextGetHuffmanTable(text);

        // 得到Huffman树
        HuffmanTree huffmanTree = fromHuffmanMapGetHuffmanTree(huffmanFrequencyMap);


        return fromHuffmanTreeGetHuffmanMap(huffmanTree);

    }


    /**
     * 利用huffman编码表写入压缩文件
     * @param context    源字符串
     * @param huffmanMap Huffman编码表
     * @param path       文件输出绝对路径
     * @throws CharMappingNotFoundException 无效字符映射异常
     * @throws FileNotFoundException        文件未找到异常
     * @throws FileCreateFailedException    无法新建文件异常
     */
    private  void writeCompressFileWithHuffmanMapping(String context, HuffmanMap huffmanMap, String path) throws CharMappingNotFoundException, FileNotFoundException, FileCreateFailedException {
        Util.writeCompressFile(huffmanMap.getBytes(context), path);
    }


    /**
     * 从字符频率编码表得到huffman树
     * @param huffmanMap 字符频率表
     */
    private HuffmanTree fromHuffmanMapGetHuffmanTree(HuffmanFrequencyMap huffmanMap) {

        // 整理成权重有序的节点list
        List<HuffmanNode> charItems = new ArrayList<>();
        for (Character c : huffmanMap.getInnerMap().keySet()) {
            charItems.add(new HuffmanNode(null, null, null, c, huffmanMap.getInnerMap().get(c)));
        }

        // list 从大到小
        Util.sortCharItemList(charItems);

        // 处理list只有一个元素的情况
        if (charItems.size() == 1) {
            return new HuffmanTree(new HuffmanNode(null, null, null, charItems.get(0).getValue(), charItems.get(0).getWeight()));
        }

        // 正常情况
        while (charItems.size() > 1) {
            // 拿到两个最小的
            HuffmanNode one     = charItems.get(charItems.size() - 1);
            HuffmanNode another = charItems.get(charItems.size() - 2);

            // list中剔除这两个
            charItems.remove(charItems.size() - 1);
            charItems.remove(charItems.size() - 1);

            // 得到权重的和节点添加进list
            HuffmanNode parent  = new HuffmanNode(null, one, another, null, HuffmanNode.getSumWeight(one, another));
            one.setParent(parent);
            another.setParent(parent);

            charItems.add(parent);

            // 使list有序
            Util.sortCharItemList(charItems);

        }

        // 此时list只剩一个元素, 即为根节点
        return new HuffmanTree(charItems.get(0));


    }


    /**
     * 从哈夫曼树得到哈夫曼编码表
     * @param huffmanTree 哈夫曼树
     * @return HuffmanMap 哈夫曼编码表
     * @throws GetErrorCharException 非法字符异常
     */
    private HuffmanMap fromHuffmanTreeGetHuffmanMap(HuffmanTree huffmanTree) throws GetErrorCharException {

        // 结果表
        HuffmanMap result = new HuffmanMap();

        // Huffman树根节点
        HuffmanNode node = (HuffmanNode) huffmanTree.getRoot();

        // 递归得到结果
        innerMakeHuffmanMap(node, result);

        // 反转全部编码
        result.reverseAllCode();

        return result;
    }

    /**
     * 递归得到编码表(规定 左0 右1)
     * @param root 子树根节点
     * @param result 哈夫曼编码表
     * @throws GetErrorCharException 非法字符异常
     */
    private void innerMakeHuffmanMap(HuffmanNode root, HuffmanMap result) throws GetErrorCharException {

        HuffmanNode currentNode = root;

        if (currentNode.isLeaf()) {
            // 如果使叶子节点表示是某个字符, 回溯得到编码
            StringBuilder codeStringBuilder = new StringBuilder();

            while (currentNode.getParent() != null) {
                // 根据节点的左右情况得到倒序码值
                int parentStatus = currentNode.getSide();
                switch (parentStatus) {
                    case 1:
                        codeStringBuilder.append("1");
                        break;
                    case -1:
                        codeStringBuilder.append("0");
                        break;
                }

                currentNode = (HuffmanNode) currentNode.getParent();

            }

            // 存储当前码值字符映射到Huffman编码表
            result.put(root.getValue(), codeStringBuilder.toString());

            return;
        }

        if (root.getLeftChild() != null) {
            innerMakeHuffmanMap((HuffmanNode) root.getLeftChild(), result);
        }

        if (root.getRightChild() != null) {
            innerMakeHuffmanMap((HuffmanNode) root.getRightChild(), result);
        }
    }


    /**
     * 从文件内容得到频率表
     * @param text 文件内容
     * @return HuffmanTable
     */
    private  HuffmanFrequencyMap fromTextGetHuffmanTable(String text) {

        char[] textArray = text.toCharArray();

        // 频率结果表
        HuffmanFrequencyMap resultMap = new HuffmanFrequencyMap();

        // 遍历文件内容
        for (int i = 0; i < text.length(); i++) {
            resultMap.put(textArray[i]);
        }

        return resultMap;
    }




}
