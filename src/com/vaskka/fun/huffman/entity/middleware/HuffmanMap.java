package com.vaskka.fun.huffman.entity.middleware;


import java.util.List;

import com.google.gson.Gson;
import com.vaskka.fun.huffman.core.format.CodingMappingJsonData;
import com.vaskka.fun.huffman.exceptions.CharMappingNotFoundException;
import com.vaskka.fun.huffman.exceptions.GetErrorByteException;
import com.vaskka.fun.huffman.exceptions.GetErrorCharException;
import com.vaskka.fun.huffman.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @program: HuffmanCodeTree
 * @description: HuffmanMap 哈夫曼编码结果表
 * @author: Vaskka
 * @create: 2018/11/28 8:00 PM
 **/

public class HuffmanMap {


    /**
     * 编码后最长码值
     */
    private int maxLength = 0;

    /**
     * 内置编码表
     */
    private HashMap<Character, byte[]> innerMap;

    public HuffmanMap() {
        innerMap = new HashMap<>();
    }


    /**
     * 存入内置表
     * @param c 字符
     * @param code 对应哈夫曼编码
     */
    public void put(char c, String code) throws GetErrorCharException {
        // 记录当前最长长度
        if (code.length() > maxLength) {
            maxLength = code.length();
        }

        byte[] bytes = new byte[code.length()];
        char[] chars = code.toCharArray();

        // 字符数组转bytes
        for (int i = 0; i < bytes.length; i++) {
            if (chars[i] == '0') {
                bytes[i] = 0;
            }
            else {
                if (chars[i] == '1') {
                    bytes[i] = 1;
                }
                else {
                    throw new GetErrorCharException("出现非法字符", chars[i]);
                }
            }
        }


        innerMap.put(c, bytes);
    }


    /**
     * 转换编码表到json
     * @return json
     */
    public String toJsonString() throws GetErrorByteException {

        List<CodingMappingJsonData.InnerMappingCoding> list = new ArrayList<>(innerMap.size());

        for (Character c : innerMap.keySet()) {

            StringBuilder stringBuilder = new StringBuilder();

            // 转字符数组到String
            for (byte b : innerMap.get(c)) {
                if (b == 1) {
                    stringBuilder.append("1");
                }
                else {
                    if (b == 0) {
                        stringBuilder.append("0");
                    }
                    else {
                        throw new GetErrorByteException("出现非法字节", b);
                    }

                }
            }

            CodingMappingJsonData.InnerMappingCoding mappingCoding = new CodingMappingJsonData.InnerMappingCoding(String.valueOf(c),stringBuilder.toString());

            list.add(mappingCoding);
        }

        CodingMappingJsonData jsonData = new CodingMappingJsonData(list);


        return (new Gson()).toJson(jsonData);
    }

    /**
     * 得到字节数组
     * @param context 上下文
     * @return byte[] 得到结果
     */
    public byte[] getBytes(String context) throws CharMappingNotFoundException {
        // 上下文字符数组
        char[] chars = context.toCharArray();

        // 结果数组
        List<Byte> bytes = new ArrayList<>();

        // 填充结果数组
        for (char c : chars) {
            if (!innerMap.containsKey(c)) {
                throw new CharMappingNotFoundException("源文件中未找到制定字符的编码", c);
            }

            // 填充bytes
            byte[] currentBytes = innerMap.get(c);
            for (byte currentByte : currentBytes) {
                bytes.add(currentByte);
            }

        }

        // 转换为bytes
        byte[] resultBytes = new byte[bytes.size()];

        int i = 0;
        for (Byte b : bytes) {
            resultBytes[i] = b;
            i++;
        }

        return resultBytes;
    }


    /**
     * 检查某组编码在映射中
     * @param bytes 编码
     * @return Character
     */
    public Character isCodeInMapping(byte[] bytes) {

        for (Character c : innerMap.keySet()) {
            byte[] bList = innerMap.get(c);

            if (Util.compareTwoBytesList(bytes, bList)) {
                return c;
            }
        }

        return null;

    }

    /**
     * 反转全部编码
     */
    public void reverseAllCode() {
        for (Character c : innerMap.keySet()) {
            byte[] values = innerMap.get(c);

            byte[] resultValues = new byte[values.length];

            for (int i = 0; i < values.length; i++) {
                resultValues[values.length - i - 1] = values[i];
            }

            System.arraycopy(resultValues, 0, values, 0, values.length);
        }
    }

}
