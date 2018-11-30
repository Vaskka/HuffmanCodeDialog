package com.vaskka.fun.huffman.exceptions;

/**
 * @program: HuffmanCodeTree
 * @description: FileWriteFailedException 文件写入异常
 * @author: Vaskka
 * @create: 2018/11/30 3:56 PM
 **/

public class FileWriteFailedException extends Exception {

    private String path;

    public FileWriteFailedException(String message, String path) {
        super(message);
        this.path = path;
    }


}
