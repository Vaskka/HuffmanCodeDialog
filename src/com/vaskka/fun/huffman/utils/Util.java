package com.vaskka.fun.huffman.utils;

import com.vaskka.fun.huffman.entity.huffman.HuffmanNode;
import com.vaskka.fun.huffman.entity.base.Node;
import com.vaskka.fun.huffman.exceptions.FileCreateFailedException;
import com.vaskka.fun.huffman.exceptions.FileWriteFailedException;

import java.util.*;
import java.io.*;

/**
 * @program: HuffmanCodeTree
 * @description: Util 工具类
 * @author: Vaskka
 * @create: 2018/11/28 4:47 PM
 **/

public class Util {

    /**
     * 读取文件内容
     * @param path 路径
     * @return 文件内容
     * @throws FileNotFoundException 文件不存在异常
     */
    public static String readFile(String path) throws FileNotFoundException {
        File file = new File(path);

        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }

        // 结果字符串
        StringBuilder stringBuilder = new StringBuilder();

        // 读取文件
        try (Reader reader = new InputStreamReader(new FileInputStream(file)) ) {

            // 一次读一个字符
            int tempChar;
            while ((tempChar = reader.read()) != -1) {
                // 屏蔽Windows下的\r
                if (((char) tempChar) != '\r') {
                    stringBuilder.append((char) tempChar);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return stringBuilder.toString();

    }


    /**
     * 对charItems降序
     * @param li HuffmanNode List
     */
    public static void sortCharItemList(List<HuffmanNode> li) {
        li.sort((o1, o2) -> {
            if (o1.getWeight() > o2.getWeight()) {
                return -1;
            }
            else {
                if (o1.getWeight() < o2.getWeight()) {
                    return 1;
                }
                else {
                    if (Node.getChildrenNumber(o1) < Node.getChildrenNumber(o2)) {
                        return 1;
                    }
                    else {
                        if (Node.getChildrenNumber(o1) > Node.getChildrenNumber(o2)) {
                            return -1;
                        }
                        else {
                            return 0;
                        }

                    }

                }
            }

        });

    }


    /**
     * 写入文本文件
     * @param text 文本内容
     * @param filePath 文件路径
     * @throws FileCreateFailedException 无法创建新文件异常
     * @throws FileWriteFailedException  文件写入异常
     */
    public static void writeFile(String text, String filePath) throws FileCreateFailedException, FileWriteFailedException {

        File file =new File(filePath);

        // 文件不存在则新建
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new FileCreateFailedException("无法创建新文件:" + e.getMessage(), filePath);
            }
        }

        // 写入字符串到文件
        try (FileWriter fileWriter = new FileWriter(file.getAbsolutePath())){

            fileWriter.write(text);

        }catch(IOException e){
            throw new FileWriteFailedException("文件写入异常:" + e.getMessage(), filePath);
        }
    }


    /**
     * 得到文件所在文件夹
     * @param sourcePath 源文件路径
     * @return 文件夹路径
     */
    public static String getFileDirectoryPath(String sourcePath) {
        File file = new File(sourcePath);
        return file.getParent();
    }


    /**
     * 路径获取文件名
     * @param sourcePath 源文件路径
     * @return 文件路径
     */
    public static String getFileName(String sourcePath) {
        File file = new File(sourcePath);
        return file.getName();
    }


    /**
     * 为文件名结尾添加固定字符串
     * @param fileName 带修改的文件名
     * @return String 修改后的文件名
     */
    public static String appendFileName(String fileName, String appendString) {
        String[] sList = fileName.split("\\.");
        String format = sList[sList.length - 1];

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sList.length - 1; i++) {
            result.append(sList[i]);
        }
        result.append(appendString);
        result.append(".");
        result.append(format);

        return result.toString();
    }

    /**
     * 得到输出的字符映射json文件路径
     * @param sourcePath 源文件路径
     * @return 字符映射文件的路径
     */
    public static String getOutputJsonName(String sourcePath) {

        // 提取扩展名之前文件路径
        String[] sList = sourcePath.split("\\.");

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sList.length - 1; i++) {
            stringBuilder.append(sList[i]);
        }

        // 在输出json结尾附加固定字符串
        stringBuilder.append(Const.OUTPUT_COMPRESS_MAPPING + ".json");

        return stringBuilder.toString();

    }


    /**
     * 得到压缩后的输出二进制文件名
     * @param sourcePath 源文件文件名
     * @return 结果文件名
     */
    public static String getOutputCompressedFilePath(String sourcePath) {
        // 提取扩展名之前文件路径
        String[] sList = sourcePath.split("\\.");

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sList.length - 1; i++) {
            stringBuilder.append(sList[i]);
        }

        // 在输出json结尾附加固定字符串
        stringBuilder.append(Const.OUTPUT_COMPRESS + ".huffman");

        return stringBuilder.toString();
    }

    /**
     * 写入压缩文件
     * @param data 写入的数据
     * @param destinationPath 目标文件名
     */
    public static void writeCompressFile(byte[] data, String destinationPath) throws FileCreateFailedException, FileNotFoundException {
        File file = new File(destinationPath);

        // 不存在创建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new FileCreateFailedException("文件新建失败:" + e.getMessage(), file.getAbsolutePath());
            }
        }


        // 写入字节数组
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(data);
        }
        catch (IOException e) {
            throw new FileNotFoundException("文件未找到:" + e.getMessage());
        }


    }


    /**
     * 读取压缩文件
     * @param sourceFile 压缩文件 路径
     * @return 读取的文件内容
     */
    public static List<Integer> readCompressFile(String sourceFile) {
        File file = new File(sourceFile);


        List<Integer> contents = new ArrayList<>(1024);

        try(InputStream in = new FileInputStream(file)) {

            int tempByte;

            while ((tempByte = in.read()) != -1) {
                contents.add(tempByte);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        return contents;
    }


    /**
     * 得到两个字节数组是否相等
     * @param bytes1 byte[]
     * @param bytes2 byte[]
     * @return boolean
     */
    public static boolean compareTwoBytesList(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length != bytes2.length) {
            return false;
        }

        for (int i = 0; i < bytes1.length; i++) {
            if (bytes2[i] != bytes1[i]) {
                return false;
            }
        }

        return true;
    }


    /**
     * 得到输出的原文件
     * @param compressedPath 压缩文件路径
     * @return 源文件路径
     */
    public static String getOutputSourceFilePath(String compressedPath) {

        String[] list = compressedPath.split("\\.");

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.length - 1; i++) {
            stringBuilder.append(list[i]);
        }

        stringBuilder.append(Const.OUTPUT_UNPACK_MAPPING);
        stringBuilder.append(".txt");

        return stringBuilder.toString();

    }


    /**
     * 检查两个文件的内容是否相同
     * @param path1 第一个文件的路径
     * @param path2 第二个文件的路径
     * @return boolean
     * @throws IOException IOException
     */
    public static boolean checkTwoFileIsEquals(String path1, String path2) throws IOException {
        String s1 = Util.readFile(path1);
        String s2 = Util.readFile(path2);

        return s1.equals(s2);
    }


}
