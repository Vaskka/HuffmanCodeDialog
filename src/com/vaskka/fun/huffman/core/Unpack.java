package com.vaskka.fun.huffman.core;

import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import com.vaskka.fun.huffman.core.format.CodingMappingJsonData;
import com.vaskka.fun.huffman.entity.middleware.HuffmanMap;
import com.vaskka.fun.huffman.exceptions.*;
import com.vaskka.fun.huffman.utils.Util;

/**
 * @program: HuffmanCodeTree
 * @description: Unpack 解压
 * @author: Vaskka
 * @create: 2018/11/30 8:05 PM
 **/

public class Unpack {

    private static Unpack unpack;

    private Unpack() {}

    public static Unpack getInstance() {
        if (unpack == null) {
            unpack = new Unpack();
        }

        return unpack;
    }


    /**
     * 利用压缩文件和清单文件得到源文件
     * @param compressedPath 压缩文件路径
     * @param jsonPath 清单文件路径
     */
    public void doUnpack(String compressedPath, String jsonPath) throws IOException, GetErrorCharException, ParseJsonMappingException, FileWriteFailedException, FileCreateFailedException {
        // 二进制序列
        List<Integer> list = Util.readCompressFile(compressedPath);

        // 读取json
        String json = Util.readFile(jsonPath);

        // 解析得到编码表
        HuffmanMap huffmanMap = fromJsonGetHuffmanMap(json);

        // 得到明文
        String text = fromHuffmanMapGetSourceString(huffmanMap, list);

        // 构造文件路径（生成在压缩文件统一文件夹）
        String sourceFileName = Util.getOutputSourceFilePath(compressedPath);

        // 写入文件
        Util.writeFile(text, sourceFileName);

    }


    /**
     * 从json得到哈夫曼映射文件
     * @param json json字符串
     * @return HuffmanMap
     * @throws GetErrorCharException     解析json时code出现非法字符异常
     * @throws ParseJsonMappingException 解析json文件发生异常
     */
    private HuffmanMap fromJsonGetHuffmanMap(String json) throws GetErrorCharException, ParseJsonMappingException {
        CodingMappingJsonData jsonData = (new Gson()).fromJson(json, CodingMappingJsonData.class);

        HuffmanMap huffmanMap = new HuffmanMap();

        for (CodingMappingJsonData.InnerMappingCoding item : jsonData.getCodeMapping()) {
            // 判断key长度
            if (item.getC().length() != 1) {
                throw new ParseJsonMappingException("映射文件的key长错误", json, item.getC());
            }

            // 解析映射的二进制
            // 插入HashMap
            huffmanMap.put(item.getC().charAt(0), item.getCoding());
        }

        return huffmanMap;
    }


    /**
     * 利用哈夫曼编码和字节List得到明文
     * @param huffmanMap Huffman编码表
     * @param byteList   byteList
     * @return 明文
     */
    private String fromHuffmanMapGetSourceString(HuffmanMap huffmanMap, List<Integer> byteList) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int start = 0; start < byteList.size(); start++) {
            int offset;
            for (offset = start; offset < byteList.size(); offset++) {
                Character c = getPositionIsInHuffmanMap(huffmanMap, byteList, start, offset);

                if (c != null) {
                    stringBuilder.append(c);
                    break;
                }
            }

            // start 偏移到offset
            start = offset;
        }

        return stringBuilder.toString();


    }


    /**
     * 得到区间是否有code在编码表中
     * @param huffmanMap 哈夫曼编码表
     * @param byteList 字节List
     * @param start 开始position
     * @param end 结束字节
     * @return Character
     */
    private Character getPositionIsInHuffmanMap(HuffmanMap huffmanMap, List<Integer> byteList, int start, int end) {
        if (start > end || byteList.size() - 1 < end) {
            throw new IndexOutOfBoundsException("检查区间时存在越界");
        }

        // 得到区间的字节数组
        byte[] testList = new byte[end - start + 1];
        int j = 0;
        for (int i = start; i <= end; i++) {
            testList[j] = (byte) byteList.get(i).intValue();
            j++;
        }


        // 检查是否在编码表中
        return huffmanMap.isCodeInMapping(testList);
    }



}
