package com.vaskka.fun.huffman.exceptions;

/**
 * @program: HuffmanCodeTree
 * @description: FileCreateFailedException 无法新建文件异常
 * @author: Vaskka
 * @create: 2018/11/30 3:52 PM
 **/

public class FileCreateFailedException extends  Exception {
    private String path;

    public FileCreateFailedException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
