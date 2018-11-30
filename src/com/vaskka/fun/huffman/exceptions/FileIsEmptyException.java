package com.vaskka.fun.huffman.exceptions;

import java.io.File;

/**
 * @program: HuffmanCodeTree
 * @description: FileIsEmptyException 文件内容为空异常
 * @author: Vaskka
 * @create: 2018/11/28 7:41 PM
 **/

public class FileIsEmptyException extends Exception {

    private File file;

    public FileIsEmptyException(File f, String message) {
        super(message);
        this.file = f;

    }

    public File getFile() {
        return file;
    }

    @Override
    public String getMessage() {
        String resultMessage =  super.getMessage() + " Error file is " + file.getAbsolutePath();
        return  resultMessage;
    }
}
